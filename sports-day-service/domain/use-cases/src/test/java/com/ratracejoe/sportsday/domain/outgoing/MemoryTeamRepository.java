package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.ITeamRepository;
import java.util.List;
import java.util.UUID;

public class MemoryTeamRepository implements ITeamRepository {
  private final MemoryGenericRepository<Team> genericRepository;

  public MemoryTeamRepository() {
    this.genericRepository = new MemoryGenericRepository<>(Team.class, Team::id);
  }

  @Override
  public Team getById(UUID id) throws NotFoundException {
    return genericRepository.getById(id);
  }

  @Override
  public List<Team> getAll() {
    return genericRepository.getAll();
  }

  @Override
  public void save(Team activity) {
    genericRepository.save(activity);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    genericRepository.deleteById(id);
  }
}
