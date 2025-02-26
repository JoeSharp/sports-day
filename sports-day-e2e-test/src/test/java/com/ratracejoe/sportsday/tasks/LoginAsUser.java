package com.ratracejoe.sportsday.tasks;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.model.LoginResponseDTO;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class LoginAsUser implements Task {
  public static final String KEY_ACCESS_TOKEN = "accessToken";
  public static final String KEY_REFRESH_TOKEN = "refreshToken";

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
}
