package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;

public class ActivityCommandService implements ICommandHandler {
  private final IActivityService activityService;
  private final IResponseListener responseListener;

  public ActivityCommandService(
      final IActivityService activityService, final IResponseListener responseListener) {
    this.activityService = activityService;
    this.responseListener = responseListener;
  }

  public void handleCommand(String command) throws InvalidCommandException {}
}
