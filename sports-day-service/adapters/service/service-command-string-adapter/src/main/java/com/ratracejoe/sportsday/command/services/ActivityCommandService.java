package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ActivityCommandService implements ICommandHandler {
  private final IActivityService activityService;
  private final IResponseListener responseListener;
  private final Map<String, ICommandHandler> commandHandlers;

  public ActivityCommandService(
      final IActivityService activityService, final IResponseListener responseListener) {
    this.activityService = activityService;
    this.responseListener = responseListener;
    this.commandHandlers =
        Map.of("add", this::addActivity, "getAll", this::getAll, "getById", this::getById);
  }

  public void handleCommand(String commandStr) throws InvalidCommandException {
    Command command = Command.fromString(commandStr);

    if (!commandHandlers.containsKey(command.opcode())) {
      throw new InvalidCommandException();
    }

    commandHandlers.get(command.opcode()).handleCommand(command.operand());
  }

  private void getById(String input) throws InvalidCommandException {
    try {
      UUID id = UUID.fromString(input);
      Activity activity = activityService.getById(id);
      respondWithActivity(activity);
    } catch (Exception e) {
      throw new InvalidCommandException();
    }
  }

  private void getAll(String input) throws InvalidCommandException {
    if (!input.isEmpty()) {
      throw new InvalidCommandException();
    }
    activityService.getAll().forEach(this::respondWithActivity);
  }

  private void respondWithActivity(Activity activity) {
    String response =
        String.format(
            "Activity id: '%s', name: '%s', description: '%s'",
            activity.id(), activity.name(), activity.description());
    responseListener.handleResponse(response);
  }

  private void addActivity(String input) throws InvalidCommandException {
    List<String> parts = Command.splitRespectingQuotes(input);
    if (parts.size() != 2) {
      throw new InvalidCommandException();
    }
    activityService.createActivity(parts.get(0), parts.get(1));
  }
}
