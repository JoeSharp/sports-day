package com.ratracejoe.sportsday.repository.jpa.entity;

import com.ratracejoe.sportsday.domain.model.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {
  @Id private UUID id;

  private UUID activityId;
  private EventState state;
  private CompetitorType competitorType;
  private ScoreType scoreType;
  private int maxParticipant;

  public static EventEntity domainToEntity(Event domain) {
    return new EventEntity(
        domain.id(),
        domain.activityId(),
        domain.state(),
        domain.competitorType(),
        domain.scoreType(),
        domain.maxParticipants());
  }

  public static Event entityToDomain(EventEntity entity) {
    return new Event(
        entity.getId(),
        entity.getActivityId(),
        entity.getState(),
        entity.getCompetitorType(),
        entity.getScoreType(),
        entity.getMaxParticipant());
  }
}
