package com.ratracejoe.sportsday.domain.exception;

import java.util.UUID;

public class ActivityNotFoundException extends RuntimeException {
  public ActivityNotFoundException(UUID id) {
    super(String.format("Activity %s not found", id));
  }
}
