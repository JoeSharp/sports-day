package com.ratracejoe.sportsday.api.tasks;

import static com.ratracejoe.sportsday.Constants.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.Constants.KEY_CREATED_ACTIVITY_ID;

import com.ratracejoe.sportsday.model.ActivityDTO;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.junit.jupiter.api.DisplayName;

@RequiredArgsConstructor
@DisplayName("Create a new activity")
public class CreateActivity implements Task {
  private final ActivityDTO activityDTO;

  public static CreateActivity withActivity(ActivityDTO activityDTO) {
    return new CreateActivity(activityDTO);
  }

  @Override
  public <T extends Actor> void performAs(T t) {
    String accessToken = t.recall(KEY_ACCESS_TOKEN);
    t.attemptsTo(
        Post.to("/api/activities")
            .with(
                r ->
                    r.header("Authorization", "Bearer " + accessToken)
                        .accept(ContentType.JSON)
                        .contentType(ContentType.JSON)
                        .body(activityDTO)));
    ActivityDTO response = SerenityRest.lastResponse().jsonPath().getObject(".", ActivityDTO.class);
    t.remember(KEY_CREATED_ACTIVITY_ID, response.id());
  }
}
