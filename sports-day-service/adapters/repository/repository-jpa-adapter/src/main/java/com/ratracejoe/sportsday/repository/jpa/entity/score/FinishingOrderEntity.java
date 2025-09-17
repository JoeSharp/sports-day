package com.ratracejoe.sportsday.repository.jpa.entity.score;

import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "score_finishing_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinishingOrderEntity {
  @Id private UUID eventId;

  @ElementCollection
  @CollectionTable(name = "ordered_finishers", joinColumns = @JoinColumn(name = "event_id"))
  @Column(name = "finisher")
  private List<UUID> finishers;

  public static FinishingOrderEntity domainToEntity(FinishingOrder domain) {
    return new FinishingOrderEntity(domain.eventId(), domain.finishers());
  }

  public static FinishingOrder entityToDomain(FinishingOrderEntity entity) {
    return new FinishingOrder(entity.eventId, entity.finishers);
  }
}
