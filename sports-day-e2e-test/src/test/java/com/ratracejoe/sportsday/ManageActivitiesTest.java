package com.ratracejoe.sportsday;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.tasks.*;
import java.util.UUID;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Managing Activities")
public class ManageActivitiesTest {
  private Actor serviceMonitor;
  private Actor serviceUser;

  @BeforeEach
  public void beforeEach() {
    EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    var forEnv = EnvironmentSpecificConfiguration.from(variables);

    String restApiBaseUrl = forEnv.getProperty("accounts.service.url");
    String username = forEnv.getProperty("service.username");
    String password = forEnv.getProperty("service.password");
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(CallAnApi.at(restApiBaseUrl))
            .whoCan(Authenticate.withCredentials(username, password));

    serviceMonitor = Actor.named("MrDavies").whoCan(CallAnApi.at(restApiBaseUrl));

    serviceUser.attemptsTo(LoginAsUser.create());
  }

  @AfterEach
  public void afterEach() {
    serviceUser.attemptsTo(CleanUpActivities.create());
  }

  @Test
  @DisplayName("A user that is not logged in is denied access to activities")
  public void getActivitiesDenied() {
    serviceMonitor.attemptsTo(GetActivities.create());
    serviceMonitor.should(
        seeThatResponse("Access Denied", r -> r.statusCode(HttpStatus.SC_UNAUTHORIZED)));
  }

  @Test
  @DisplayName("A logged in user can retrieve activities")
  public void getActivities() {
    serviceUser.attemptsTo(GetActivities.create());
    serviceUser.should(
        seeThatResponse("Activities Retrieved", r -> r.statusCode(HttpStatus.SC_OK)));
  }

  @Test
  @DisplayName("A logged in user can create then retrieve a single activity")
  public void createActivity() {
    ActivityDTO newActivity = new ActivityDTO("Dancing " + UUID.randomUUID(), "Moving to music");
    serviceUser.attemptsTo(CreateActivity.withActivity(newActivity));
    serviceUser.should(
        seeThatResponse("Activity Created", r -> r.statusCode(HttpStatus.SC_CREATED)));

    UUID createdId = serviceUser.recall(CreateActivity.KEY_CREATED_ACTIVITY_ID);
    assertThat(createdId).isNotNull();
    serviceUser.attemptsTo(GetActivity.forId(createdId));
    serviceUser.should(
        seeThatResponse(
            "Created Activity Retrieved",
            r -> r.statusCode(HttpStatus.SC_OK).body("name", equalTo(newActivity.name()))));
  }

  @Test
  @DisplayName("A created activity appears in the list of activities")
  public void createdActivityInList() {
    ActivityDTO newActivity = new ActivityDTO("Dancing " + UUID.randomUUID(), "Moving to music");
    serviceUser.attemptsTo(CreateActivity.withActivity(newActivity));
    serviceUser.should(
        seeThatResponse("Activity Created", r -> r.statusCode(HttpStatus.SC_CREATED)));

    UUID createdId = serviceUser.recall(CreateActivity.KEY_CREATED_ACTIVITY_ID);
    assertThat(createdId).isNotNull();
    serviceUser.attemptsTo(GetActivities.create());
    serviceUser.should(
        seeThatResponse("Activity in List", r -> r.body("id", hasItems(createdId.toString()))));
  }

  @Test
  @DisplayName("A logged in user can create then delete a single activity")
  public void deleteActivity() {
    ActivityDTO newActivity = new ActivityDTO("Curling " + UUID.randomUUID(), "Speed sweeping ice");
    serviceUser.attemptsTo(CreateActivity.withActivity(newActivity));

    UUID createdId = serviceUser.recall(CreateActivity.KEY_CREATED_ACTIVITY_ID);
    serviceUser.attemptsTo(DeleteActivity.forId(createdId));
    serviceUser.should(seeThatResponse("Activity Deleted", r -> r.statusCode(HttpStatus.SC_OK)));
  }
}
