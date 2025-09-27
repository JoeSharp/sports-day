package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;

public class TeamCommandService implements ICommandHandler {
  private final ITeamService teamService;
  private final IResponseListener responseListener;

  public TeamCommandService(
      final ITeamService teamService, final IResponseListener responseListener) {
    this.teamService = teamService;
    this.responseListener = responseListener;
  }

  @Override
  public void handleCommand(String commandStr) throws InvalidCommandException {}
}
