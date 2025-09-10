package com.ratracejoe.sportsday.repository.jpa.service;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.repository.ITeamRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.TeamEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class TeamRepositoryJpaImpl extends GenericRepositoryJpaImpl<Team, TeamEntity>
    implements ITeamRepository {
  public TeamRepositoryJpaImpl(JpaRepository<TeamEntity, UUID> repository) {
    super(repository, TeamEntity::domainToEntity, TeamEntity::entityToDomain);
  }
}
