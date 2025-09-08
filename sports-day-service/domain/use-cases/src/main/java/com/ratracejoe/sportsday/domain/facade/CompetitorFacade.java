package com.ratracejoe.sportsday.domain.facade;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.incoming.ICompetitorFacade;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import java.util.UUID;

public class CompetitorFacade implements ICompetitorFacade {
  private final IGenericRepository<Competitor> competitorRepository;

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
  public Competitor getById(UUID id) throws NotFoundException {
    return competitorRepository.getById(id);
  }
}
