package com.ratracejoe.sportsday.domain.model.score;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

public record PointScoreSheet(UUID eventId, Map<UUID, Integer> scores) {
  public static PointScoreSheet create(UUID eventId) {
    return new PointScoreSheet(eventId, Collections.emptyMap());
  }

  private Function<Integer, Integer> incrementBy(Integer by) {
    return v -> v + by;
  }

  private BiFunction<UUID, Integer, Integer> createOrIncrement(Integer increment) {
    return (uuid, value) ->
        Optional.ofNullable(value).map(incrementBy(increment)).orElse(increment);
  }

  public PointScoreSheet withScoreIncrement(UUID competitorId, Integer increment) {
    Map<UUID, Integer> newScores = new HashMap<>(scores);
    newScores.compute(competitorId, createOrIncrement(increment));
    return new PointScoreSheet(this.eventId, newScores);
  }
}
