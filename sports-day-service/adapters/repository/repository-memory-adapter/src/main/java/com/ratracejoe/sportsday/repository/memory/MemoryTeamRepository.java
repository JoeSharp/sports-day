package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.repository.ITeamRepository;

public class MemoryTeamRepository extends MemoryGenericRepository<Team> implements ITeamRepository {

  public MemoryTeamRepository() {
    super(Team.class, Team::id);
  }
}
