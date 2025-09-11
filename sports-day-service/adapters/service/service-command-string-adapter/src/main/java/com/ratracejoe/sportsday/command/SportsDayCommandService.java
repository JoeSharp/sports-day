package com.ratracejoe.sportsday.command;

import com.ratracejoe.sportsday.command.services.ActivityCommandService;
import com.ratracejoe.sportsday.command.services.CompetitorCommandService;
import com.ratracejoe.sportsday.command.services.EventCommandService;
import com.ratracejoe.sportsday.command.services.TeamCommandService;

public class SportsDayCommandService implements ICommandHandler {
  private final ActivityCommandService activityService;
  private final CompetitorCommandService competitorService;
  private final EventCommandService eventService;
  private final TeamCommandService teamService;

  public SportsDayCommandService(
      final ActivityCommandService activityService,
      final CompetitorCommandService competitorService,
      final EventCommandService eventService,
      final TeamCommandService teamService) {
    this.activityService = activityService;
    this.competitorService = competitorService;
    this.eventService = eventService;
    this.teamService = teamService;
  }

    @Override
    public void handleCommand() throws InvalidCommandException {

    }
}
