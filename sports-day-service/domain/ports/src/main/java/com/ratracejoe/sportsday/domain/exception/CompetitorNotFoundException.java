package com.ratracejoe.sportsday.domain.exception;

import java.util.UUID;

public class CompetitorNotFoundException extends RuntimeException {
  public CompetitorNotFoundException(UUID id) {
    super(String.format("Competitor %s not found", id));
  }
}
