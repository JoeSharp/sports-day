package com.ratracejoe.sportsday.tasks;

import static com.ratracejoe.sportsday.tasks.LoginAsUser.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.tasks.LoginAsUser.KEY_REFRESH_TOKEN;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

public class LogoutAsUser implements Task {

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(KEY_ACCESS_TOKEN);
    String refreshToken = t.recall(KEY_REFRESH_TOKEN);

    t.attemptsTo(
        Post.to("/api/auth/logout")
            .with(
                r ->
                    r.accept(ContentType.JSON)
                        .contentType(ContentType.URLENC)
                        .header("Authorization", "Bearer " + accessToken)
                        .formParam("refreshToken", refreshToken)));
  }

  public static LogoutAsUser create() {
    return new LogoutAsUser();
  }
}
