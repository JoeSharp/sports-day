package com.ratracejoe.sportsday;

import static com.ratracejoe.sportsday.Constants.*;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.tasks.ui.LoginViaUI;
import com.ratracejoe.sportsday.tasks.ui.TableExists;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.time.Duration;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.targets.Target;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;
import org.openqa.selenium.WebDriver;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("/features")
public class CucumberTestSuite {

  @Managed private WebDriver driver;

  private Actor serviceUser;

  @Given("{word} can login")
  public void userCanLogin(String user) {
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(BrowseTheWeb.with(driver))
            .whoCan(CallAnApi.at(REST_API_BASE_URL))
            .whoCan(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD));
  }

  @When("user types in their credentials")
  public void userTypesInTheirCredentials() {
    serviceUser.attemptsTo(Open.url(UI_BASE_URL), LoginViaUI.create());
  }

  private final Target ACTIVITIES_HEADING =
      ElementLocated.by("css:h2").waitingForNoMoreThan(Duration.ofSeconds(5));

  @Then("activities table is present")
  public void activitiesTableIsPresent() {
    Ensure.that(serviceUser.asksFor(TableExists.withId("activities"))).isTrue();
  }
}
