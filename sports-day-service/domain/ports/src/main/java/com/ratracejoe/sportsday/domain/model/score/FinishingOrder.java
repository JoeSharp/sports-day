package com.ratracejoe.sportsday.domain.model.score;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record FinishingOrder(UUID eventId, List<UUID> finishers) {
  public static FinishingOrder create(UUID eventId) {
    return new FinishingOrder(eventId, Collections.emptyList());
  }

  public FinishingOrder withFinisher(UUID competitorId) {
    List<UUID> newFinishers = new ArrayList<>();
    newFinishers.addAll(this.finishers);
    newFinishers.add(competitorId);
    return new FinishingOrder(this.eventId, newFinishers);
  }
}
