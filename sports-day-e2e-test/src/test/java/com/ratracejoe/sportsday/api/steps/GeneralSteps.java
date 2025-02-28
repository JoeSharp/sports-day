package com.ratracejoe.sportsday.api.steps;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.rest.questions.LastResponse;

public class GeneralSteps {
  @Then("should receive HTTP status {int}")
  public void shouldReceiveHttpStatus(int status) {
    Ensure.that(LastResponse.received().answeredBy(OnStage.theActorInTheSpotlight()).statusCode())
        .isEqualTo(status);
  }
}
