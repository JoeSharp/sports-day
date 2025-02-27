package com.ratracejoe.sportsday;

import static com.ratracejoe.sportsday.Constants.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.tasks.api.CleanUpActivities;
import com.ratracejoe.sportsday.tasks.api.LoginToApi;
import com.ratracejoe.sportsday.tasks.ui.CreateActivityUI;
import com.ratracejoe.sportsday.tasks.ui.LoginViaUI;
import java.time.Duration;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.targets.Target;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Manage Activities via the UI")
public class UserInterfaceTests {
  @Managed private WebDriver driver;

  private Actor serviceUser;

  private final Target ACTIVITIES_HEADING =
      ElementLocated.by("css:h2").waitingForNoMoreThan(Duration.ofSeconds(5));

  @BeforeEach
  public void beforeEach() {
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(BrowseTheWeb.with(driver))
            .whoCan(CallAnApi.at(REST_API_BASE_URL))
            .whoCan(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD));
  }

  @AfterEach
  public void afterEach() {
    serviceUser.attemptsTo(LoginToApi.create());
    serviceUser.attemptsTo(CleanUpActivities.create());
    serviceUser.should(seeThatResponse("Activity Deleted", r -> r.statusCode(HttpStatus.SC_OK)));
  }

  @Test
  @DisplayName("Can login via UI")
  public void loginViaUi() {

    serviceUser.attemptsTo(
        Open.url(UI_BASE_URL), LoginViaUI.create(), Ensure.that(ACTIVITIES_HEADING).isDisplayed());
  }

  @Test
  @DisplayName("Can create an activity")
  public void createActivity() {
    ActivityDTO newActivity = ActivityDTO.randomActivity();

    serviceUser.attemptsTo(
        Open.url(UI_BASE_URL),
        LoginViaUI.create(),
        Ensure.that(ACTIVITIES_HEADING).isDisplayed(),
        CreateActivityUI.withActivity(newActivity));
  }
}
