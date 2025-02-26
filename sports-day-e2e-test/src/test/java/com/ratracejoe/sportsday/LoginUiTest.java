package com.ratracejoe.sportsday;

import static com.ratracejoe.sportsday.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.abilities.Authenticate;
import net.serenitybdd.annotations.Managed;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Login via UI")
public class LoginUiTest {
  @Managed private WebDriver driver;

  private Actor serviceUser;

  @BeforeEach
  public void beforeEach() {
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(BrowseTheWeb.with(driver))
            .whoCan(CallAnApi.at(REST_API_BASE_URL))
            .whoCan(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD));
  }

  @Test
  @DisplayName("Can login via UI")
  public void loginViaUi() {
    serviceUser.attemptsTo(Open.browserOn().thePageNamed("pages.https"));
    serviceUser.attemptsTo(
        Enter.theValue(SERVICE_USERNAME).into(InputField.withNameOrId("username")));
    serviceUser.attemptsTo(
        Enter.theValue(SERVICE_PASSWORD).into(InputField.withNameOrId("password")));
    serviceUser.attemptsTo(Click.on(Button.withText("Login")));
    var heading = Text.of("css:h2");
    assertThat(heading).contains("Activities");
  }
}
