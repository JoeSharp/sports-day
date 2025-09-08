package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.jpa.model.CompetitorEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class CompetitorRepositoryJpaImpl
    extends GenericRepositoryJpaImpl<Competitor, CompetitorEntity> {
  public CompetitorRepositoryJpaImpl(JpaRepository<CompetitorEntity, UUID> repository) {
    super(repository, CompetitorEntity::domainToEntity, CompetitorEntity::entityToDomain);
  }
}
