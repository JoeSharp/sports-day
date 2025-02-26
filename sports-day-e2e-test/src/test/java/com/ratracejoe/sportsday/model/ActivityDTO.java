package com.ratracejoe.sportsday.model;

import java.util.UUID;

public record ActivityDTO(UUID id, String name, String description) {
  public ActivityDTO(String name, String description) {
    this(null, name, description);
  }
}
