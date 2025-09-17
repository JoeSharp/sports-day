package com.ratracejoe.sportsday.domain.model;

import java.util.UUID;

public record Event(
    UUID id,
    UUID activityId,
    EventState state,
    CompetitorType competitorType,
    ScoreType scoreType,
    int maxParticipants) {
  public Event withState(EventState newState) {
    return new Event(
        id,
        activityId,
        state.nextStateIsValid(newState),
        competitorType,
        scoreType,
        maxParticipants);
  }
}
