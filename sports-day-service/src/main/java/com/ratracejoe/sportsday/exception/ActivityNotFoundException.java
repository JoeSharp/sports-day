package com.ratracejoe.sportsday.exception;

import java.util.UUID;

public class ActivityNotFoundException extends Exception {
  public ActivityNotFoundException(UUID id) {
    super(String.format("Activity %s not found", id));
  }
}
