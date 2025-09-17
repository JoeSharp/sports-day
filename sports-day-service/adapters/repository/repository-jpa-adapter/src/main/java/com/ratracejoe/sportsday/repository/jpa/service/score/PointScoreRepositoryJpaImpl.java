package com.ratracejoe.sportsday.repository.jpa.service.score;

import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.score.PointScoreSheetEntity;
import com.ratracejoe.sportsday.repository.jpa.service.GenericRepositoryJpaImpl;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class PointScoreRepositoryJpaImpl
    extends GenericRepositoryJpaImpl<PointScoreSheet, PointScoreSheetEntity>
    implements IPointScoreSheetRepository {

  public PointScoreRepositoryJpaImpl(JpaRepository<PointScoreSheetEntity, UUID> jpaRepository) {
    super(
        jpaRepository,
        PointScoreSheetEntity::domainToEntity,
        PointScoreSheetEntity::entityToDomain);
  }
}
