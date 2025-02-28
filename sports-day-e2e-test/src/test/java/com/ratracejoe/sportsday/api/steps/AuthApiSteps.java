package com.ratracejoe.sportsday.api.steps;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import com.ratracejoe.sportsday.api.tasks.LoginToApi;
import com.ratracejoe.sportsday.api.tasks.LogoutFromApi;
import com.ratracejoe.sportsday.api.tasks.RefreshApiToken;
import com.ratracejoe.sportsday.ui.steps.AuthUiSteps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.hamcrest.Matchers;

public class AuthApiSteps {
  @Steps private AuthUiSteps authUiSteps;
  @Steps private GeneralSteps generalSteps;

  @Given("{actor} successfully logs into API")
  public void successfullyLogsIn(Actor actor) {
    authUiSteps.userCanLogin(actor);
    theyCallLogin();
    generalSteps.shouldReceiveHttpStatus(200);
    shouldBeProvidedWithAccessToken();
  }

  @When("they call login with their credentials")
  public void theyCallLogin() {
    OnStage.theActorInTheSpotlight().attemptsTo(new LoginToApi());
  }

  @When("they call refresh with their token")
  public void theyCallRefresh() {
    OnStage.theActorInTheSpotlight().attemptsTo(new RefreshApiToken());
  }

  @When("they call logout")
  public void theyCallLogout() {
    OnStage.theActorInTheSpotlight().attemptsTo(new LogoutFromApi());
  }

  @Then("they should be provided with an access token")
  public void shouldBeProvidedWithAccessToken() {
    OnStage.theActorInTheSpotlight()
        .should(
            seeThatResponse(
                "Access Token Provided", r -> r.body("accessToken", Matchers.isA(String.class))));
  }
}
