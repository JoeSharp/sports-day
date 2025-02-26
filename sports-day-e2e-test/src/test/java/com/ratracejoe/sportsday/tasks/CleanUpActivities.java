package com.ratracejoe.sportsday.tasks;

import java.util.Objects;
import java.util.UUID;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;

public class CleanUpActivities implements Task {

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(LoginAsUser.KEY_ACCESS_TOKEN);
    UUID created = t.recall(CreateActivity.KEY_CREATED_ACTIVITY_ID);
    if (Objects.isNull(created)) {
      return;
    }
    t.attemptsTo(
        Delete.from("/api/activities/{id}")
            .with(
                r -> r.pathParam("id", created).header("Authorization", "Bearer " + accessToken)));
  }

  public static CleanUpActivities create() {
    return new CleanUpActivities();
  }
}
