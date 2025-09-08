package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.ITeamRepository;

public class MemoryTeamRepository extends MemoryGenericRepository<Team> implements ITeamRepository {

  public MemoryTeamRepository() {
    super(Team.class, Team::id);
  }
}
