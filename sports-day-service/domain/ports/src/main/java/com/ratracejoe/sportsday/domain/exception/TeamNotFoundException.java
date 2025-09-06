package com.ratracejoe.sportsday.domain.exception;

import java.util.UUID;

public class TeamNotFoundException extends RuntimeException {
  public TeamNotFoundException(UUID id) {
    super(String.format("Team %s not found", id));
  }
}
