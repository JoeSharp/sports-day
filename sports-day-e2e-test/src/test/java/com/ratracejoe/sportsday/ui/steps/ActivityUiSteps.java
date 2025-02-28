package com.ratracejoe.sportsday.ui.steps;

import static com.ratracejoe.sportsday.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.api.tasks.CleanUpActivities;
import com.ratracejoe.sportsday.api.tasks.LoginToApi;
import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.ui.questions.ButtonExistsWithActivityId;
import com.ratracejoe.sportsday.ui.questions.TableExists;
import com.ratracejoe.sportsday.ui.tasks.CreateActivityUI;
import com.ratracejoe.sportsday.ui.tasks.DeleteActivityUI;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.UUID;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;

public class ActivityUiSteps {
  @After
  public void cleanup() {
    OnStage.theActorInTheSpotlight()
        .whoCan(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD))
        .attemptsTo(LoginToApi.create(), CleanUpActivities.create());
  }

  @Then("activities table is present")
  public void activitiesTableIsPresent() {
    OnStage.theActorInTheSpotlight()
        .attemptsTo(
            Ensure.that("Activities table exists", TableExists.withId(ID_TABLE_ACTIVITIES))
                .isTrue());
  }

  @Given("they generate a random activity")
  public void givenRandomActivityGenerated() {
    ActivityDTO newActivity = ActivityDTO.randomActivity();

    OnStage.theActorInTheSpotlight().remember(KEY_CREATED_ACTIVITY, newActivity);
  }

  @When("they create the activity")
  public void whenUserCreatesActivity() {
    ActivityDTO newActivity = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY);
    OnStage.theActorInTheSpotlight().attemptsTo(CreateActivityUI.withActivity(newActivity));
  }

  @Then("the activity is shown in the table")
  public void thenActivityIsShownInTheTable() {
    UUID activityId = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY_ID);

    assertThat(activityId).isNotNull();
    OnStage.theActorInTheSpotlight()
        .attemptsTo(
            Ensure.that("The new ID is in the table", ButtonExistsWithActivityId.withId(activityId))
                .isTrue());
  }

  @When("they delete that activity")
  public void theyDeleteThatActivity() {
    UUID createdId = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY_ID);
    OnStage.theActorInTheSpotlight().attemptsTo(DeleteActivityUI.withId(createdId));
  }

  @Then("the activity is removed from the table")
  public void theActivityIsRemovedFromTable() {
    UUID deletedId = OnStage.theActorInTheSpotlight().recall(KEY_DELETED_ACTIVITY_ID);

    OnStage.theActorInTheSpotlight()
        .attemptsTo(
            Ensure.that("The new ID is in the table", ButtonExistsWithActivityId.withId(deletedId))
                .isFalse());
  }
}
