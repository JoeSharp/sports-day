package com.ratracejoe.sportsday.api.steps;

import static com.ratracejoe.sportsday.Constants.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

import com.ratracejoe.sportsday.api.tasks.CreateActivity;
import com.ratracejoe.sportsday.api.tasks.DeleteActivity;
import com.ratracejoe.sportsday.api.tasks.GetActivities;
import com.ratracejoe.sportsday.api.tasks.GetActivity;
import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.ui.steps.ActivityUiSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.UUID;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.questions.LastResponse;
import org.apache.http.HttpStatus;

public class ActivityApiSteps {
  @Steps private ActivityUiSteps activityUiSteps;

  @Steps private GeneralSteps generalSteps;

  @When("User {actor} GET the list of activities")
  public void getListOfActivities(Actor actor) {
    actor.attemptsTo(GetActivities.create());
  }

  @When("they GET the list of activities")
  public void getListOfActivities() {
    OnStage.theActorInTheSpotlight().attemptsTo(GetActivities.create());
  }

  @Then("the activities were retrieved")
  public void activitiesRetrieved() {
    Ensure.that(LastResponse.received().answeredBy(OnStage.theActorInTheSpotlight()).statusCode())
        .isEqualTo(HttpStatus.SC_OK);
  }

  @Given("a random activity is created")
  public void aRandomActivityIsCreated() {
    activityUiSteps.givenRandomActivityGenerated();
    postActivity();
    generalSteps.shouldReceiveHttpStatus(200);
  }

  @When("they POST that activity")
  public void postActivity() {
    ActivityDTO newActivity = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY);
    OnStage.theActorInTheSpotlight().attemptsTo(CreateActivity.withActivity(newActivity));
  }

  @When("they GET the created activity")
  public void getCreatedActivity() {
    UUID createdId = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY_ID);
    assertThat(createdId).isNotNull();
    OnStage.theActorInTheSpotlight().attemptsTo(GetActivity.forId(createdId));
  }

  @Then("the new activity ID is retrieved")
  public void newActivityIsRetrieved() {
    ActivityDTO newActivity = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY);
    OnStage.theActorInTheSpotlight()
        .should(
            seeThatResponse(
                "Created Activity Retrieved",
                r -> r.statusCode(HttpStatus.SC_OK).body("name", equalTo(newActivity.name()))));
  }

  @Then("the new activity ID appears in that list")
  public void newActivityAppearsInList() {
    UUID activityId = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY_ID);

    OnStage.theActorInTheSpotlight()
        .should(
            seeThatResponse(
                "Activity in List", r -> r.body("id", hasItems(activityId.toString()))));
  }

  @When("they DELETE that activity")
  public void theyDeleteThatActivity() {
    UUID createdId = OnStage.theActorInTheSpotlight().recall(KEY_CREATED_ACTIVITY_ID);
    OnStage.theActorInTheSpotlight().attemptsTo(DeleteActivity.forId(createdId));
    OnStage.theActorInTheSpotlight().forget(KEY_CREATED_ACTIVITY_ID);
  }

  @Then("the deleted activity ID does not appear in that list")
  public void deletedActivityNotInList() {
    UUID activityId = OnStage.theActorInTheSpotlight().recall(KEY_DELETED_ACTIVITY_ID);

    OnStage.theActorInTheSpotlight()
        .should(
            seeThatResponse(
                "Activity not in List", r -> r.body("id", not(hasItems(activityId.toString())))));
  }
}
