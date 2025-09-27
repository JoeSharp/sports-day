package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;

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

  private void createEvent(String input) {}

  @Override
  String domainToLogString(Competitor domain) {
    return String.format("Competitor id: %s, name: %s", domain.id(), domain.name());
  }
}
