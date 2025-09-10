package com.ratracejoe.sportsday.repository.jpa.service;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.CompetitorEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class CompetitorRepositoryJpaImpl
    extends GenericRepositoryJpaImpl<Competitor, CompetitorEntity>
    implements ICompetitorRepository {
  public CompetitorRepositoryJpaImpl(JpaRepository<CompetitorEntity, UUID> repository) {
    super(repository, CompetitorEntity::domainToEntity, CompetitorEntity::entityToDomain);
  }
}
