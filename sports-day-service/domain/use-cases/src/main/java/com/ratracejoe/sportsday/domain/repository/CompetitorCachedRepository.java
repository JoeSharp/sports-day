package com.ratracejoe.sportsday.domain.repository;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;

public class CompetitorCachedRepository extends CachedRepository<Competitor, ICompetitorRepository>
    implements ICompetitorRepository {
  public CompetitorCachedRepository(
      ICompetitorRepository persistent, ICompetitorRepository cached) {
    super(persistent, cached);
  }
}
