package com.ratracejoe.sportsday.domain.model;

import java.util.UUID;

public record Event(
    UUID id,
    UUID activityId,
    EventState state,
    ParticipantType participantType,
    int maxParticipants) {
  public Event withState(EventState newState) {
    return new Event(
        id, activityId, state.nextStateIsValid(newState), participantType, maxParticipants);
  }
}
