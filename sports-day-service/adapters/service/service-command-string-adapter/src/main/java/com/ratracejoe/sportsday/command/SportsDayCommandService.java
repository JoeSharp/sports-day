package com.ratracejoe.sportsday.command;

import com.ratracejoe.sportsday.command.services.ActivityCommandService;
import com.ratracejoe.sportsday.command.services.CompetitorCommandService;
import com.ratracejoe.sportsday.command.services.EventCommandService;
import com.ratracejoe.sportsday.command.services.TeamCommandService;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;

public class SportsDayCommandService implements ICommandHandler {
  private final ActivityCommandService activityService;
  private final CompetitorCommandService competitorService;
  private final EventCommandService eventService;
  private final TeamCommandService teamService;

  public SportsDayCommandService(
      IResponseListener responseListener,
      IActivityService activityService,
      ICompetitorService competitorService,
      IEventService eventService,
      ITeamService teamService) {
    this.activityService = new ActivityCommandService(activityService, responseListener);
    this.competitorService = new CompetitorCommandService(competitorService, responseListener);
    this.eventService = new EventCommandService(eventService, responseListener);
    this.teamService = new TeamCommandService(teamService, responseListener);
  }

  @Override
  public void handleCommand(String command) throws InvalidCommandException {}
}
