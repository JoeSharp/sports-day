package com.ratracejoe.sportsday.api.tasks;

import static com.ratracejoe.sportsday.Constants.KEY_ACCESS_TOKEN;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.junit.jupiter.api.DisplayName;

@RequiredArgsConstructor
@DisplayName("Get the list of activities")
public class GetActivities implements Task {

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(KEY_ACCESS_TOKEN);
    t.attemptsTo(
        Get.resource("/api/activities")
            .with(r -> r.header("Authorization", "Bearer " + accessToken)));
  }

  public static GetActivities create() {
    return new GetActivities();
  }
}
