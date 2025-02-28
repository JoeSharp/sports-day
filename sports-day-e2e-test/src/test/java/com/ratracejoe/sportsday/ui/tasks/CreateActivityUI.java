package com.ratracejoe.sportsday.ui.tasks;

import static com.ratracejoe.sportsday.Constants.ID_TABLE_ACTIVITIES;
import static com.ratracejoe.sportsday.Constants.KEY_CREATED_ACTIVITY_ID;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.ui.questions.ActivityIdsInTable;
import com.ratracejoe.sportsday.ui.questions.ListSizeComparison;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.ui.Button;
import net.serenitybdd.screenplay.ui.InputField;
import org.junit.jupiter.api.DisplayName;

@RequiredArgsConstructor
@DisplayName("Create an Activity via the UI")
public class CreateActivityUI implements Task {
  private final ActivityDTO activityDTO;

  public static CreateActivityUI withActivity(ActivityDTO activityDTO) {
    return new CreateActivityUI(activityDTO);
  }

  @Override
  public <T extends Actor> void performAs(T t) {
    List<String> activityIdsBefore =
        t.asksFor(
            ActivityIdsInTable.forTableWithAttribute(ID_TABLE_ACTIVITIES, "data-activity-id"));

    t.attemptsTo(
        Enter.theValue(activityDTO.name()).into(InputField.withNameOrId("newActivityName")),
        Enter.theValue(activityDTO.description())
            .into(InputField.withNameOrId("newActivityDescription")),
        Click.on(Button.withText("Add Activity")));

    List<String> activityIdsAfter =
        t.asksFor(
            ActivityIdsInTable.forTableWithAttribute(ID_TABLE_ACTIVITIES, "data-activity-id"));

    t.should(
        seeThat(
            "The list of activities has increased in size by 1",
            ListSizeComparison.hasOneMoreItemThan(activityIdsAfter, activityIdsBefore)));

    UUID activityId = UUID.fromString(activityIdsAfter.getLast());
    t.remember(KEY_CREATED_ACTIVITY_ID, activityId);
  }
}
