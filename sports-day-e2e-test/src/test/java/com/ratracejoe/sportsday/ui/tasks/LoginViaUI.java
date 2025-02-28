package com.ratracejoe.sportsday.ui.tasks;

import com.ratracejoe.sportsday.abilities.Authenticate;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Login via UI")
public class LoginViaUI implements Task {
  @Override
  public <T extends Actor> void performAs(T t) {
    Authenticate auth = Authenticate.as(t);
    t.attemptsTo(
        Enter.theValue(auth.username()).into(InputField.withNameOrId("username")),
        Enter.theValue(auth.password()).into(InputField.withNameOrId("password")),
        Click.on(Button.withText("Login")));
  }

  public static LoginViaUI create() {
    return new LoginViaUI();
  }
}
