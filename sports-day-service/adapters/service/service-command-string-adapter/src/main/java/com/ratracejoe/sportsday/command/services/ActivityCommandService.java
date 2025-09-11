package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;

public class ActivityCommandService implements ICommandHandler {
  private final IActivityService activityService;

  public ActivityCommandService(final IActivityService activityService) {
    this.activityService = activityService;
  }

    @Override
    public void handleCommand() throws InvalidCommandException {

    }
}
