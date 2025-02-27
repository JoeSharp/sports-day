package com.ratracejoe.sportsday.model;

import java.util.List;
import java.util.UUID;

public record ActivityDTO(UUID id, String name, String description) {
  public ActivityDTO(String name, String description) {
    this(null, name, description);
  }

  private static final List<String> BASE_ACTIVITY_NAMES =
      List.of("Curling", "Dancing", "Football", "Basketball", "Pole Vault", "Shotput");

  private static String appendRand(String base) {
    return String.format("%s-%s", base, UUID.randomUUID());
  }

  public static ActivityDTO randomActivity() {
    return new ActivityDTO(
        appendRand(
            BASE_ACTIVITY_NAMES.get((int) Math.floor(Math.random() * BASE_ACTIVITY_NAMES.size()))),
        appendRand("doing something energetic"));
  }
}
