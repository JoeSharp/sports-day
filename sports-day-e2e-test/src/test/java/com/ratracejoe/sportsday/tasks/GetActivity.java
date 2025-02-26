package com.ratracejoe.sportsday.tasks;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

@RequiredArgsConstructor
public class GetActivity implements Task {
  final UUID id;

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(LoginAsUser.KEY_ACCESS_TOKEN);
    t.attemptsTo(
        Get.resource("/api/activities/{id}")
            .with(r -> r.pathParam("id", id).header("Authorization", "Bearer " + accessToken)));
  }

  public static GetActivity forId(UUID id) {
    return new GetActivity(id);
  }
}
