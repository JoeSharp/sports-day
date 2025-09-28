package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;

public class TeamCommandService extends GenericCommandService<Team> {
  private final ITeamService teamService;

  public TeamCommandService(
      final ITeamService teamService, final IResponseListener responseListener) {
    super(responseListener);
    this.teamService = teamService;
    registerHandler("create", this::createTeam);
    registerHandler("getAll", getAllHandler(teamService::getAll));
    registerHandler("getById", getByIdHandler(teamService::getById));
    registerHandler("deleteById", deleteHandler(teamService::deleteById));
  }

  private void createTeam(String input) {
    teamService.createTeam(Command.stripQuotes(input));
  }

  @Override
  String domainToLogString(Team domain) {
    return String.format("Team id: %s, name: %s", domain.id(), domain.name());
  }
}
