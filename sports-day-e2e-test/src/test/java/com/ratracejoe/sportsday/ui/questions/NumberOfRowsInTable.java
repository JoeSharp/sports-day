package com.ratracejoe.sportsday.ui.questions;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.targets.Target;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Number of Rows in Table")
@RequiredArgsConstructor
public class NumberOfRowsInTable implements Question<Integer> {

  private final String tableId;

  @Override
  public Integer answeredBy(Actor actor) {
    Target tableRows = Target.the("table rows").locatedBy("//table[@id='" + tableId + "']//tr");
    return tableRows.resolveAllFor(actor).size();
  }

  public static NumberOfRowsInTable forTableId(String tableId) {
    return new NumberOfRowsInTable(tableId);
  }
}
