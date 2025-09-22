package com.ratracejoe.sportsday.repository.jpa.entity.score;

import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import jakarta.persistence.*;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "score_timed_finishing_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimedFinishingOrderEntity {
  @Id private UUID eventId;

  @ElementCollection
  @CollectionTable(name = "timed_finishers", joinColumns = @JoinColumn(name = "event_id"))
  @MapKeyColumn(name = "finisher_id")
  @Column(name = "score")
  private Map<UUID, Long> finishTimeMilliseconds;

  public static TimedFinishingOrderEntity domainToEntity(TimedFinishingOrder domain) {
    return new TimedFinishingOrderEntity(domain.eventId(), domain.finishTimeMilliseconds());
  }

  public static TimedFinishingOrder entityToDomain(TimedFinishingOrderEntity entity) {
    return new TimedFinishingOrder(entity.eventId, entity.finishTimeMilliseconds);
  }
}
