package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import java.util.UUID;

public class CompetitorService implements ICompetitorService {
  private final ICompetitorRepository competitorRepository;

  public CompetitorService(ICompetitorRepository competitorRepository) {
    this.competitorRepository = competitorRepository;
  }

  @Override
  public Competitor createCompetitor(CompetitorType type, String name) {
    Competitor competitor = new Competitor(UUID.randomUUID(), type, name);
    competitorRepository.save(competitor);
    return competitor;
  }

  @Override
  public Competitor getById(UUID id) throws NotFoundException {
    return competitorRepository.getById(id);
  }
}
