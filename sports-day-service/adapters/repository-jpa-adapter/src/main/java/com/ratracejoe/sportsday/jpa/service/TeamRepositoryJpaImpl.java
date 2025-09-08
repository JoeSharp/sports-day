package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.jpa.model.TeamEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class TeamRepositoryJpaImpl extends GenericRepositoryJpaImpl<Team, TeamEntity> {
  public TeamRepositoryJpaImpl(JpaRepository<TeamEntity, UUID> repository) {
    super(repository, TeamEntity::domainToEntity, TeamEntity::entityToDomain);
  }
}
