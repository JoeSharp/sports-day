package com.ratracejoe.sportsday.command;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
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
  void passesCommandOnCorrectly() throws InvalidCommandException {
    // When
    commandService.handleCommand("activity add swimming in-water-init");

    // Then
    verify(activityService).createActivity("swimming", "in-water-init");
  }
}
