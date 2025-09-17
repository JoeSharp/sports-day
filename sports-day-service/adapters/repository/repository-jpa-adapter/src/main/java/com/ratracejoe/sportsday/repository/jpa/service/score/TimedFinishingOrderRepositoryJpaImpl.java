package com.ratracejoe.sportsday.repository.jpa.service.score;

import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.score.TimedFinishingOrderEntity;
import com.ratracejoe.sportsday.repository.jpa.service.GenericRepositoryJpaImpl;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class TimedFinishingOrderRepositoryJpaImpl
    extends GenericRepositoryJpaImpl<TimedFinishingOrder, TimedFinishingOrderEntity>
    implements ITimedFinishingOrderRepository {

  public TimedFinishingOrderRepositoryJpaImpl(
      JpaRepository<TimedFinishingOrderEntity, UUID> jpaRepository) {
    super(
        jpaRepository,
        TimedFinishingOrderEntity::domainToEntity,
        TimedFinishingOrderEntity::entityToDomain);
  }
}
