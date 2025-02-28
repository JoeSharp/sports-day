package com.ratracejoe.sportsday.api.steps;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class HealthSteps {
  @When("{actor} requests system health")
  public void requestsSystemHealth(Actor actor) {
    actor.attemptsTo(Get.resource("/api/actuator/health"));
  }

  @Then("the system reports health {word}")
  public void systemHealthReported(String expected) {
    OnStage.theActorInTheSpotlight()
        .should(
            seeThatResponse(
                "Health is UP",
                r -> r.statusCode(HttpStatus.SC_OK).body("status", Matchers.equalTo(expected))));
  }
}
