package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;

public class CompetitorCommandService implements ICommandHandler {
  private final ICompetitorService competitorService;

  public CompetitorCommandService(final ICompetitorService competitorService) {
    this.competitorService = competitorService;
  }

  @Override
  public void handleCommand() throws InvalidCommandException {}
}
