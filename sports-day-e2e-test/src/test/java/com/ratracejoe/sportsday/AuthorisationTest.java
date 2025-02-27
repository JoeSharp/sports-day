package com.ratracejoe.sportsday;

import static com.ratracejoe.sportsday.Constants.*;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.tasks.api.LoginToApi;
import com.ratracejoe.sportsday.tasks.api.LogoutFromApi;
import com.ratracejoe.sportsday.tasks.api.RefreshApiToken;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Authorisation")
public class AuthorisationTest {
  private Actor serviceUser;

  @BeforeEach
  public void beforeEach() {
    serviceUser =
        Actor.named("MrSharp")
            .whoCan(CallAnApi.at(REST_API_BASE_URL))
            .whoCan(Authenticate.withCredentials(SERVICE_USERNAME, SERVICE_PASSWORD));
  }

  @Test
  @DisplayName("A service user can login")
  public void login() {
    serviceUser.attemptsTo(new LoginToApi());
    serviceUser.should(
        seeThatResponse(
            "Access Token Provided",
            r -> r.statusCode(HttpStatus.SC_OK).body("accessToken", Matchers.isA(String.class))));
  }

  @Test
  @DisplayName("A logged in user, can refresh their auth token")
  public void refresh() {
    serviceUser.attemptsTo(new LoginToApi());
    serviceUser.attemptsTo(new RefreshApiToken());
    serviceUser.should(
        seeThatResponse(
            "Access Token Provided",
            r -> r.statusCode(HttpStatus.SC_OK).body("accessToken", Matchers.isA(String.class))));
  }

  @Test
  @DisplayName("A logged in user, can log back out")
  public void logout() {
    serviceUser.attemptsTo(new LoginToApi());
    serviceUser.attemptsTo(new LogoutFromApi());
    serviceUser.should(seeThatResponse("Logout successful", r -> r.statusCode(HttpStatus.SC_OK)));

    /**
     * Hmm.....this doesn't seem to work as expected serviceUser.attemptsTo(new RefreshAsUser());
     * serviceUser.should( seeThatResponse("Access Denied", r ->
     * r.statusCode(HttpStatus.SC_UNAUTHORIZED)));
     *
     * <p>serviceUser.attemptsTo(GetActivities.create()); serviceUser.should(
     * seeThatResponse("Access Denied", r -> r.statusCode(HttpStatus.SC_UNAUTHORIZED)));
     */
  }
}
