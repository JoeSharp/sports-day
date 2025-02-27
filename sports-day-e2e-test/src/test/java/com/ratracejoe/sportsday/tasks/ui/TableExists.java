package com.ratracejoe.sportsday.tasks.ui;

import java.time.Duration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Table Exists with ID")
public class TableExists implements Question<Boolean> {
  private final String id;

  public TableExists(String id) {
    this.id = id;
  }

  @Override
  public Boolean answeredBy(Actor actor) {
    return ElementLocated.by("//table[@id='" + id + "']")
        .waitingForNoMoreThan(Duration.ofSeconds(5))
        .isVisibleFor(actor);
  }

  public static TableExists withId(String id) {
    return new TableExists(id);
  }
}
