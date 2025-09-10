package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;

public class MemoryCompetitorRepository extends MemoryGenericRepository<Competitor>
    implements ICompetitorRepository {

  public MemoryCompetitorRepository() {
    super(Competitor.class, Competitor::id);
  }
}
