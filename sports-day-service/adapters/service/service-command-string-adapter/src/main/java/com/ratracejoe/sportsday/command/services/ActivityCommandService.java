package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import java.util.Map;

public class ActivityCommandService implements ICommandHandler {
  private final IActivityService activityService;
  private final IResponseListener responseListener;
  private final Map<String, ICommandHandler> commandHandlers;

  public ActivityCommandService(
      final IActivityService activityService, final IResponseListener responseListener) {
    this.activityService = activityService;
    this.responseListener = responseListener;
    this.commandHandlers = Map.of("add", this::addActivity);
  }

  public void handleCommand(String commandStr) throws InvalidCommandException {
    Command command = Command.fromString(commandStr);

    if (!commandHandlers.containsKey(command.opcode())) {
      throw new InvalidCommandException();
    }

    commandHandlers.get(command.opcode()).handleCommand(command.operand());
  }

  private void addActivity(String commandStr) throws InvalidCommandException {
    String[] parts = commandStr.split(" ");
    if (parts.length != 2) {
      throw new InvalidCommandException();
    }
    activityService.createActivity(parts[0], parts[1]);
  }
}
