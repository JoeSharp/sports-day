package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import java.util.List;

public class CompetitorCommandService extends GenericCommandService<Competitor> {
  private final ICompetitorService competitorService;

  public CompetitorCommandService(
      final ICompetitorService competitorService, final IResponseListener responseListener) {
    super(responseListener);
    this.competitorService = competitorService;
    registerHandler("create", this::createEvent);
    registerHandler("getAll", getAllHandler(competitorService::getAll));
    registerHandler("getById", getByIdHandler(competitorService::getById));
  }

  private void createEvent(String input) throws InvalidCommandException {
    List<String> parts = Command.splitRespectingQuotes(input);
    if (parts.size() != 2) {
      throw new InvalidCommandException();
    }
    try {
      CompetitorType type = CompetitorType.valueOf(parts.get(0));
      competitorService.createCompetitor(type, parts.get(1));
    } catch (IllegalArgumentException e) {
      throw new InvalidCommandException();
    }
  }

  @Override
  String domainToLogString(Competitor domain) {
    return String.format("Competitor id: %s, name: %s", domain.id(), domain.name());
  }
}
