package com.ratracejoe.sportsday;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.tasks.*;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class AuthorisationTest {
  private Actor serviceUser;

  @BeforeEach
  public void beforeEach() {
    EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    var forEnv = EnvironmentSpecificConfiguration.from(variables);

    String restApiBaseUrl = forEnv.getProperty("accounts.service.url");
    String username = forEnv.getProperty("service.username");
    String password = forEnv.getProperty("service.password");
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(CallAnApi.at(restApiBaseUrl))
            .whoCan(Authenticate.withCredentials(username, password));
  }

  @Test
  public void login() {
    serviceUser.attemptsTo(new LoginAsUser());
    serviceUser.should(
        seeThatResponse(
            "Access Token Provided",
            r -> r.statusCode(HttpStatus.SC_OK).body("accessToken", Matchers.isA(String.class))));
  }

  @Test
  public void refresh() {
    serviceUser.attemptsTo(new LoginAsUser());
    serviceUser.attemptsTo(new RefreshAsUser());
    serviceUser.should(
        seeThatResponse(
            "Access Token Provided",
            r -> r.statusCode(HttpStatus.SC_OK).body("accessToken", Matchers.isA(String.class))));
  }

  @Test
  public void logout() {
    serviceUser.attemptsTo(new LoginAsUser());
    serviceUser.attemptsTo(new LogoutAsUser());
    serviceUser.should(seeThatResponse("Logout successful", r -> r.statusCode(HttpStatus.SC_OK)));
  }
}
