package com.ratracejoe.sportsday;

import static com.ratracejoe.sportsday.Constants.REST_API_BASE_URL;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.openqa.selenium.WebDriver;

public class ParameterDefinitions {
  @Managed private WebDriver driver;

  @ParameterType(".*")
  public Actor actor(String actorName) {
    return OnStage.theActorCalled(actorName);
  }

  @Before
  public void setTheStage() {
    OnStage.setTheStage(
        OnlineCast.whereEveryoneCan(BrowseTheWeb.with(driver), CallAnApi.at(REST_API_BASE_URL)));
  }
}
