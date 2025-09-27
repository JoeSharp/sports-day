package com.ratracejoe.sportsday.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SportsDayCommandServiceTest {
  private IResponseListener responseListener;
  private IActivityService activityService;
  private ICompetitorService competitorService;
  private IEventService eventService;
  private ITeamService teamService;
  private SportsDayCommandService commandService;

  @BeforeEach
  void beforeEach() {
    responseListener = mock(IResponseListener.class);
    activityService = mock(IActivityService.class);
    competitorService = mock(ICompetitorService.class);
    eventService = mock(IEventService.class);
    teamService = mock(ITeamService.class);
    commandService =
        new SportsDayCommandService(
            responseListener, activityService, competitorService, eventService, teamService);
  }

  @Test
  void addActivity() throws InvalidCommandException {
    // When
    commandService.handleCommand("activity add swimming \"in water init\"");

    // Then
    verify(activityService).createActivity("swimming", "in water init");
  }

  @Test
  void getActivityById() throws InvalidCommandException {
    // When
    Activity activity = new Activity(UUID.randomUUID(), "running", "getting sweaty");
    when(activityService.getById(activity.id())).thenReturn(activity);
    commandService.handleCommand("activity getById " + activity.id());

    // Then
    verify(activityService, times(1)).getById(activity.id());
    verify(responseListener, times(1)).handleResponse(any(String.class));
  }

  @Test
  void getAllActivities() throws InvalidCommandException {
    // When
    List<Activity> activities =
        List.of(
            new Activity(UUID.randomUUID(), "running", "getting sweaty"),
            new Activity(UUID.randomUUID(), "swimming", "getting wet"),
            new Activity(UUID.randomUUID(), "sky diving", "go splat"));
    when(activityService.getAll()).thenReturn(activities);
    commandService.handleCommand("activity getAll");

    // Then
    verify(activityService, times(1)).getAll();
    verify(responseListener, times(3)).handleResponse(any(String.class));
  }

  @Test
  void getAllInvalid() {
    // When
    assertThatThrownBy(() -> commandService.handleCommand("activity getAll foobar"))
        .isInstanceOf(InvalidCommandException.class);
  }
}
