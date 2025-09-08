package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;

public class MemoryCompetitorRepository extends MemoryGenericRepository<Competitor>
    implements ICompetitorRepository {

  public MemoryCompetitorRepository() {
    super(Competitor.class, Competitor::id);
  }
}
