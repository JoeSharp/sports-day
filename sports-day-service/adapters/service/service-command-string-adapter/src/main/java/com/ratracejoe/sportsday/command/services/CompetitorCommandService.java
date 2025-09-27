package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;

public class CompetitorCommandService implements ICommandHandler {
  private final ICompetitorService competitorService;
  private final IResponseListener responseListener;

  public CompetitorCommandService(
      final ICompetitorService competitorService, final IResponseListener responseListener) {
    this.competitorService = competitorService;
    this.responseListener = responseListener;
  }

  @Override
  public void handleCommand(String commandStr) throws InvalidCommandException {}
}
