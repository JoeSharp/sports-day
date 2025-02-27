package com.ratracejoe.sportsday.tasks.ui;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.ensure.web.ElementLocated;
import net.serenitybdd.screenplay.targets.Target;
import org.junit.jupiter.api.DisplayName;

@RequiredArgsConstructor
@DisplayName("Activity IDs in Table")
public class ActivityIdsInTable implements Question<List<String>> {
  private final String tableId;
  private final String idAttribute;

  @Override
  public List<String> answeredBy(Actor actor) {
    Target tableRows = ElementLocated.by("//table[@id='" + tableId + "']//tr");
    return tableRows.resolveAllFor(actor).stream()
        .map(row -> row.getDomAttribute(idAttribute))
        .collect(Collectors.toList());
  }

  public static ActivityIdsInTable forTableWithAttribute(String tableId, String idAttribute) {
    return new ActivityIdsInTable(tableId, idAttribute);
  }
}
