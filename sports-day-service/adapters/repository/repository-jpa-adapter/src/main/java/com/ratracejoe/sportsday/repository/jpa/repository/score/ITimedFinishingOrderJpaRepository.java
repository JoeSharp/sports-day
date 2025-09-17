package com.ratracejoe.sportsday.repository.jpa.repository.score;

import com.ratracejoe.sportsday.repository.jpa.entity.score.TimedFinishingOrderEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITimedFinishingOrderJpaRepository
    extends JpaRepository<TimedFinishingOrderEntity, UUID> {}
