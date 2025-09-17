package com.ratracejoe.sportsday.domain.model.score;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public record PointScoreSheet(UUID eventId, Map<UUID, Integer> scores) {
  public static PointScoreSheet create(UUID eventId) {
    return new PointScoreSheet(eventId, Collections.emptyMap());
  }

  public PointScoreSheet withScoreIncrement(UUID competitorId, Integer increment) {
    Map<UUID, Integer> newScores = new HashMap<>(scores);
    newScores.put(competitorId, this.scores.getOrDefault(competitorId, 0) + increment);
    return new PointScoreSheet(this.eventId, newScores);
  }
}
