package com.ratracejoe.sportsday;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;

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
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
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

    serviceUser.attemptsTo(new LoginAsUser());
  }

  @Test
  public void getActivitiesDenied() {
    serviceMonitor.attemptsTo(new GetActivities());
    serviceMonitor.should(
        seeThatResponse("Access Denied", r -> r.statusCode(HttpStatus.SC_UNAUTHORIZED)));
  }

  @Test
  public void getActivities() {
    serviceUser.attemptsTo(new GetActivities());
    serviceUser.should(
        seeThatResponse("Activities Retrieved", r -> r.statusCode(HttpStatus.SC_OK)));
  }

  @Test
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
            r ->
                r.statusCode(HttpStatus.SC_OK).body("name", Matchers.equalTo(newActivity.name()))));
  }

  @Test
  public void deleteActivity() {
    ActivityDTO newActivity = new ActivityDTO("Curling " + UUID.randomUUID(), "Speed sweeping ice");
    serviceUser.attemptsTo(CreateActivity.withActivity(newActivity));

    UUID createdId = serviceUser.recall(CreateActivity.KEY_CREATED_ACTIVITY_ID);
    serviceUser.attemptsTo(DeleteActivity.forId(createdId));
    serviceUser.should(seeThatResponse("Activity Deleted", r -> r.statusCode(HttpStatus.SC_OK)));
  }
}
