package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import java.util.UUID;

public record CompetitorDTO(UUID id, CompetitorType type, String name) {
  public static CompetitorDTO fromDomain(Competitor domain) {
    return new CompetitorDTO(domain.id(), domain.type(), domain.name());
  }
}
