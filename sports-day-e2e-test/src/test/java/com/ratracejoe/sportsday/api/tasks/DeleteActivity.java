package com.ratracejoe.sportsday.api.tasks;

import static com.ratracejoe.sportsday.Constants.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.Constants.KEY_DELETED_ACTIVITY_ID;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.junit.jupiter.api.DisplayName;

@RequiredArgsConstructor
@DisplayName("Delete a specific activity")
public class DeleteActivity implements Task {
  final UUID id;

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(KEY_ACCESS_TOKEN);
    t.attemptsTo(
        Delete.from("/api/activities/{id}")
            .with(r -> r.pathParam("id", id).header("Authorization", "Bearer " + accessToken)));
    t.remember(KEY_DELETED_ACTIVITY_ID, id);
  }

  public static DeleteActivity forId(UUID id) {
    return new DeleteActivity(id);
  }
}
