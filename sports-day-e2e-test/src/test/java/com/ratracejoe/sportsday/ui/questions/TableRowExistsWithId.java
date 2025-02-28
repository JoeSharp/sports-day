package com.ratracejoe.sportsday.ui.questions;

import java.time.Duration;
import java.util.UUID;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Table Row Exists with ID")
public class TableRowExistsWithId implements Question<Boolean> {
  private final UUID id;

  public TableRowExistsWithId(UUID id) {
    this.id = id;
  }

  @Override
  public Boolean answeredBy(Actor actor) {
    Target row =
        ElementLocated.by("//table//tr[@id='" + id + "']")
            .waitingForNoMoreThan(Duration.ofSeconds(2));
    return !Text.of(row).answeredBy(actor).isEmpty();
  }

  public static TableRowExistsWithId withId(UUID id) {
    return new TableRowExistsWithId(id);
  }
}
