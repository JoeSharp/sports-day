package com.ratracejoe.sportsday.tasks.ui;

import java.util.UUID;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
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
    Target row = Target.the("table row with id " + id).locatedBy("//table//tr[@id='" + id + "']");
    return !Text.of(row).answeredBy(actor).isEmpty();
  }

  public static TableRowExistsWithId withId(UUID id) {
    return new TableRowExistsWithId(id);
  }
}
