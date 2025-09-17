package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.EventState;
import com.ratracejoe.sportsday.domain.model.ScoreType;
import java.util.UUID;

public record EventDTO(
    UUID id,
    UUID activityId,
    EventState state,
    CompetitorType competitorType,
    ScoreType scoreType,
    int maxParticipants) {
  public static EventDTO fromDomain(Event domain) {
    return new EventDTO(
        domain.id(),
        domain.activityId(),
        domain.state(),
        domain.competitorType(),
        domain.scoreType(),
        domain.maxParticipants());
  }
}
