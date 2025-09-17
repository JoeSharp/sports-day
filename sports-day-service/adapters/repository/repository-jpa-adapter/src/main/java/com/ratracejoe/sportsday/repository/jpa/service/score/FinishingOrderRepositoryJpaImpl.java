package com.ratracejoe.sportsday.repository.jpa.service.score;

import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.score.FinishingOrderEntity;
import com.ratracejoe.sportsday.repository.jpa.service.GenericRepositoryJpaImpl;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class FinishingOrderRepositoryJpaImpl
    extends GenericRepositoryJpaImpl<FinishingOrder, FinishingOrderEntity>
    implements IFinishingOrderRepository {
  public FinishingOrderRepositoryJpaImpl(JpaRepository<FinishingOrderEntity, UUID> jpaRepository) {
    super(
        jpaRepository, FinishingOrderEntity::domainToEntity, FinishingOrderEntity::entityToDomain);
  }
}
