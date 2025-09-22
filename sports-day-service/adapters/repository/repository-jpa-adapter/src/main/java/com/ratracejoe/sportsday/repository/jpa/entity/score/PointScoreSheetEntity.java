package com.ratracejoe.sportsday.repository.jpa.entity.score;

import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import jakarta.persistence.*;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "score_points")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointScoreSheetEntity {
  @Id private UUID eventId;

  @ElementCollection
  @CollectionTable(name = "scores", joinColumns = @JoinColumn(name = "event_id"))
  @MapKeyColumn(name = "participant_id")
  @Column(name = "score")
  private Map<UUID, Integer> scores;

  public static PointScoreSheetEntity domainToEntity(PointScoreSheet domain) {
    return new PointScoreSheetEntity(domain.eventId(), domain.scores());
  }

  public static PointScoreSheet entityToDomain(PointScoreSheetEntity entity) {
    return new PointScoreSheet(entity.eventId, entity.scores);
  }
}
