package com.ratracejoe.sportsday.ui.steps;

import static com.ratracejoe.sportsday.Constants.*;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.ui.tasks.LoginViaUI;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

public class AuthUiSteps {
  @Given("{actor} can login as service user")
  public void userCanLogin(Actor actor) {
    actor
        .can(CallAnApi.at(REST_API_BASE_URL))
        .can(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD));
  }

  @And("they open the UI")
  public void theyOpenTheUi() {
    OnStage.theActorInTheSpotlight().attemptsTo(Open.url(UI_BASE_URL));
  }

  @When("they type in and submit their credentials")
  public void userTypesInTheirCredentials() {
    OnStage.theActorInTheSpotlight().attemptsTo(LoginViaUI.create());
  }
}
