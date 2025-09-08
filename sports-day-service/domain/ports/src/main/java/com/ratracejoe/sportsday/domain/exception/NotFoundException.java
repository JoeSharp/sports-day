package com.ratracejoe.sportsday.domain.exception;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
  public NotFoundException(Class<?> type, UUID id) {
    super(String.format("%s %s not found", type.getSimpleName(), id));
  }
}
