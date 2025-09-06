package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.CompetitorNotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.incoming.ICompetitorFacade;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import java.util.UUID;

public class CompetitorFacade implements ICompetitorFacade {
  private final ICompetitorRepository competitorRepository;

  public CompetitorFacade(ICompetitorRepository competitorRepository) {
    this.competitorRepository = competitorRepository;
  }

  @Override
  public Competitor createCompetitor(String name) {
    Competitor competitor = new Competitor(UUID.randomUUID(), name);
    competitorRepository.save(competitor);
    return competitor;
  }

  @Override
  public Competitor getById(UUID id) throws CompetitorNotFoundException {
    return competitorRepository.getById(id);
  }
}
