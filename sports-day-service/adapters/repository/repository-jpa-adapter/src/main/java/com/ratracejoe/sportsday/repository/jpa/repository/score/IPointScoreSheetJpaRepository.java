package com.ratracejoe.sportsday.repository.jpa.repository.score;

import com.ratracejoe.sportsday.repository.jpa.entity.score.PointScoreSheetEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPointScoreSheetJpaRepository extends JpaRepository<PointScoreSheetEntity, UUID> {}
