package com.ratracejoe.sportsday.command;

import static org.mockito.Mockito.*;

import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import org.junit.jupiter.api.BeforeEach;

abstract class GenericCommandServiceTest {
  protected IResponseListener responseListener;
  protected IActivityService activityService;
  protected ICompetitorService competitorService;
  protected IEventService eventService;
  protected ITeamService teamService;
  protected SportsDayCommandService commandService;

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
}
