package com.ratracejoe.sportsday.tasks.ui;

import java.time.Duration;
import java.util.UUID;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Button Exists with Activity ID")
public class ButtonExistsWithActivityId implements Question<Boolean> {
  private final UUID id;

  public ButtonExistsWithActivityId(UUID id) {
    this.id = id;
  }

  @Override
  public Boolean answeredBy(Actor actor) {
    Target row =
        Target.the("button with activity-id " + id)
            .locatedBy("//button[@data-activity-id='" + id + "']")
            .waitingForNoMoreThan(Duration.ofSeconds(5));
    return !Text.of(row).answeredBy(actor).isEmpty();
  }

  public static ButtonExistsWithActivityId withId(UUID id) {
    return new ButtonExistsWithActivityId(id);
  }
}
