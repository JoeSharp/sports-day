package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import java.util.List;
import java.util.UUID;

public class MemoryCompetitorRepository implements ICompetitorRepository {
  private final MemoryGenericRepository<Competitor> genericRepository;

  public MemoryCompetitorRepository() {
    this.genericRepository = new MemoryGenericRepository<>(Competitor.class, Competitor::id);
  }

  @Override
  public Competitor getById(UUID id) throws NotFoundException {
    return genericRepository.getById(id);
  }

  @Override
  public List<Competitor> getAll() {
    return genericRepository.getAll();
  }

  @Override
  public void save(Competitor competitor) {
    genericRepository.save(competitor);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    genericRepository.deleteById(id);
  }
}
