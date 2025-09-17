package com.ratracejoe.sportsday.rest.model;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import java.util.List;
import java.util.UUID;

public record TeamDTO(UUID id, String name, List<CompetitorDTO> members) {
  public static TeamDTO fromDomain(Team domain, List<Competitor> members) {
    List<CompetitorDTO> memberDTOs = members.stream().map(CompetitorDTO::fromDomain).toList();
    return new TeamDTO(domain.id(), domain.name(), memberDTOs);
  }
}
