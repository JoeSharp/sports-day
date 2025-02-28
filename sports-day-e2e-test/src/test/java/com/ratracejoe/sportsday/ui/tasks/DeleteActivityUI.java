package com.ratracejoe.sportsday.ui.tasks;

import static com.ratracejoe.sportsday.Constants.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

import com.ratracejoe.sportsday.ui.questions.ActivityIdsInTable;
import com.ratracejoe.sportsday.ui.questions.ListSizeComparison;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.ui.Button;
import org.junit.jupiter.api.DisplayName;

@RequiredArgsConstructor
@DisplayName("Delete and Activity via the UI")
public class DeleteActivityUI implements Task {
  private final UUID activityId;

  public static DeleteActivityUI withId(UUID activityId) {
    return new DeleteActivityUI(activityId);
  }

  @Override
  public <T extends Actor> void performAs(T t) {
    List<String> activityIdsBefore =
        t.asksFor(
            ActivityIdsInTable.forTableWithAttribute(ID_TABLE_ACTIVITIES, "data-activity-id"));

    t.attemptsTo(Click.on(Button.withNameOrId("delete-" + activityId)));

    List<String> activityIdsAfter =
        t.asksFor(
            ActivityIdsInTable.forTableWithAttribute(ID_TABLE_ACTIVITIES, "data-activity-id"));

    t.should(
        seeThat(
            "The list of activities has decreased in size by 1",
            ListSizeComparison.hasOneMoreItemThan(activityIdsBefore, activityIdsAfter)));
    t.forget(KEY_CREATED_ACTIVITY_ID);
    t.remember(KEY_DELETED_ACTIVITY_ID, activityId);
  }
}
