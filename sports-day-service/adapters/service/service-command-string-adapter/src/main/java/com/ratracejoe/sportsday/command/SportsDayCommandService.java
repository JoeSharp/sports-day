package com.ratracejoe.sportsday.command;

import com.ratracejoe.sportsday.command.services.ActivityCommandService;
import com.ratracejoe.sportsday.command.services.CompetitorCommandService;
import com.ratracejoe.sportsday.command.services.EventCommandService;
import com.ratracejoe.sportsday.command.services.TeamCommandService;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import java.util.Map;

public class SportsDayCommandService implements ICommandHandler {
  private final Map<String, ICommandHandler> commandHandlers;

  public SportsDayCommandService(
      IResponseListener responseListener,
      IActivityService activityService,
      ICompetitorService competitorService,
      IEventService eventService,
      ITeamService teamService) {
    commandHandlers =
        Map.of(
            "activity", new ActivityCommandService(activityService, responseListener),
            "competitor", new CompetitorCommandService(competitorService, responseListener),
            "team", new TeamCommandService(teamService, responseListener),
            "event", new EventCommandService(eventService, responseListener));
  }

  @Override
  public void handleCommand(String commandStr) throws InvalidCommandException {
    Command command = Command.fromString(commandStr);

    if (!commandHandlers.containsKey(command.opcode())) {
      throw new InvalidCommandException();
    }

    commandHandlers.get(command.opcode()).handleCommand(command.operand());
  }
}
