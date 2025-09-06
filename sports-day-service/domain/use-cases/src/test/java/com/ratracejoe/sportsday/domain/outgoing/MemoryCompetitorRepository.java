package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.exception.CompetitorNotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import java.util.List;
import java.util.UUID;

public class MemoryCompetitorRepository implements ICompetitorRepository {
  private final MemoryGenericRepository<Competitor, CompetitorNotFoundException> genericRepository;

  public MemoryCompetitorRepository() {
    this.genericRepository =
        new MemoryGenericRepository<>(Competitor::id, CompetitorNotFoundException::new);
  }

  @Override
  public Competitor getById(UUID id) throws CompetitorNotFoundException {
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
  public void deleteById(UUID id) throws CompetitorNotFoundException {
    genericRepository.deleteById(id);
  }
}
