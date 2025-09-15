package com.ratracejoe.sportsday.domain.model.score;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record TimedFinishingOrder(UUID eventId, Map<UUID, Long> finishTimeMilliseconds) {
  public static TimedFinishingOrder create(UUID eventId) {
    return new TimedFinishingOrder(eventId, Collections.emptyMap());
  }

  public TimedFinishingOrder withParticipant(UUID participantId, Long finishTime) {
    Map<UUID, Long> newFinishTimes = new HashMap<>();
    newFinishTimes.putAll(this.finishTimeMilliseconds);
    newFinishTimes.put(participantId, finishTime);
    return new TimedFinishingOrder(this.eventId, newFinishTimes);
  }
}
