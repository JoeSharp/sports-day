package com.ratracejoe.sportsday.api.tasks;

import static com.ratracejoe.sportsday.Constants.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.Constants.KEY_REFRESH_TOKEN;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Logout of API")
public class LogoutFromApi implements Task {

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

  public static LogoutFromApi create() {
    return new LogoutFromApi();
  }
}
