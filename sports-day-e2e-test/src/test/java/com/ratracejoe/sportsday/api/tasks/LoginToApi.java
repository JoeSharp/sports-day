package com.ratracejoe.sportsday.api.tasks;

import static com.ratracejoe.sportsday.Constants.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.Constants.KEY_REFRESH_TOKEN;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.model.LoginResponseDTO;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Login to API")
public class LoginToApi implements Task {

  @Override
  public <T extends Actor> void performAs(T t) {
    Authenticate auth = Authenticate.as(t);

    t.attemptsTo(
        Post.to("/api/auth/login")
            .with(
                r ->
                    r.accept(ContentType.JSON)
                        .contentType(ContentType.URLENC)
                        .formParam("username", auth.username())
                        .formParam("password", auth.password())));
    LoginResponseDTO loginResponse =
        SerenityRest.lastResponse().jsonPath().getObject(".", LoginResponseDTO.class);
    t.remember(KEY_ACCESS_TOKEN, loginResponse.accessToken());
    t.remember(KEY_REFRESH_TOKEN, loginResponse.refreshToken());
  }

  public static LoginToApi create() {
    return new LoginToApi();
  }
}
