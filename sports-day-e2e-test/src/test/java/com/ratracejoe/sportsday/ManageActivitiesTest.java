package com.ratracejoe.sportsday;

import static com.ratracejoe.sportsday.Constants.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.tasks.api.*;
import java.util.UUID;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
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
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(CallAnApi.at(REST_API_BASE_URL))
            .whoCan(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD));

    serviceMonitor = Actor.named("MrDavies").whoCan(CallAnApi.at(REST_API_BASE_URL));

    serviceUser.attemptsTo(LoginToApi.create());
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

    UUID createdId = serviceUser.recall(KEY_CREATED_ACTIVITY_ID);
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

    UUID createdId = serviceUser.recall(KEY_CREATED_ACTIVITY_ID);
    assertThat(createdId).isNotNull();
    serviceUser.attemptsTo(GetActivities.create());
    serviceUser.should(
        seeThatResponse("Activity in List", r -> r.body("id", hasItems(createdId.toString()))));
  }

  @Test
  @DisplayName("A logged in user can create then delete a single activity")
  public void deleteActivity() {
    ActivityDTO newActivity = ActivityDTO.randomActivity();
    serviceUser.attemptsTo(CreateActivity.withActivity(newActivity));

    UUID createdId = serviceUser.recall(KEY_CREATED_ACTIVITY_ID);
    serviceUser.attemptsTo(DeleteActivity.forId(createdId));
    serviceUser.forget(KEY_CREATED_ACTIVITY_ID);
    serviceUser.should(seeThatResponse("Activity Deleted", r -> r.statusCode(HttpStatus.SC_OK)));
  }
}
