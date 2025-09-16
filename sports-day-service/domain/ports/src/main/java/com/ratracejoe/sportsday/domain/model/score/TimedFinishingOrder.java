package com.ratracejoe.sportsday.domain.model.score;

import java.util.*;

public record TimedFinishingOrder(UUID eventId, Map<UUID, Long> finishTimeMilliseconds) {
  public static TimedFinishingOrder create(UUID eventId) {
    return new TimedFinishingOrder(eventId, Collections.emptyMap());
  }

  public TimedFinishingOrder withParticipant(UUID participantId, Long finishTime) {
    Map<UUID, Long> newFinishTimes = new HashMap<>(this.finishTimeMilliseconds);
    newFinishTimes.put(participantId, finishTime);
    return new TimedFinishingOrder(this.eventId, newFinishTimes);
  }

  public List<UUID> finishingOrder() {
    return finishTimeMilliseconds.entrySet().stream()
        .sorted(Comparator.comparingLong(Map.Entry::getValue))
        .map(Map.Entry::getKey)
        .toList();
  }
}
