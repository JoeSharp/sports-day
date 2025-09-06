package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.Activity;
import java.util.UUID;

public record ActivityDTO(UUID id, String name, String description) {
  public static ActivityDTO fromDomain(Activity domain) {
    return new ActivityDTO(domain.id(), domain.name(), domain.description());
  }
}
