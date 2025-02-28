package com.ratracejoe.sportsday.api.tasks;

import static com.ratracejoe.sportsday.Constants.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.Constants.KEY_CREATED_ACTIVITY_ID;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import java.util.Objects;
import java.util.UUID;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Delete the last activity created during the test")
public class CleanUpActivities implements Task {

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(KEY_ACCESS_TOKEN);
    UUID created = t.recall(KEY_CREATED_ACTIVITY_ID);
    if (Objects.isNull(created)) {
      return;
    }
    t.attemptsTo(
        Delete.from("/api/activities/{id}")
            .with(
                r -> r.pathParam("id", created).header("Authorization", "Bearer " + accessToken)));
    t.should(seeThatResponse("Activity Deleted", r -> r.statusCode(HttpStatus.SC_OK)));
  }

  public static CleanUpActivities create() {
    return new CleanUpActivities();
  }
}
