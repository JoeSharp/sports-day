package com.ratracejoe.sportsday.domain.repository;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.ITeamRepository;

public class TeamCachedRepository extends CachedRepository<Team, ITeamRepository>
    implements ITeamRepository {
  public TeamCachedRepository(ITeamRepository persistent, ITeamRepository cached) {
    super(persistent, cached);
  }
}
