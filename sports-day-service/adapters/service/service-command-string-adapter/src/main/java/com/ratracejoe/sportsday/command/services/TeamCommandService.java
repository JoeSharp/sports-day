package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;

public class TeamCommandService implements ICommandHandler {
  private final ITeamService teamService;

  public TeamCommandService(final ITeamService teamService) {
    this.teamService = teamService;
  }

    @Override
    public void handleCommand() throws InvalidCommandException {

    }
}
