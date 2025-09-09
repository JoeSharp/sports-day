package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.fixtures.MemoryRelationshipRepository;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.IMembershipRepository;
import com.ratracejoe.sportsday.ports.outgoing.ITeamRepository;
import java.util.List;
import java.util.UUID;

public class MemoryMembershipRepository implements IMembershipRepository {

  private final MemoryRelationshipRepository<Team, Competitor> relationshipRepository;

  public MemoryMembershipRepository(
      ITeamRepository teamRepository, ICompetitorRepository competitorRepository) {
    this.relationshipRepository =
        new MemoryRelationshipRepository<>(teamRepository, competitorRepository);
  }

  @Override
  public void addMembership(UUID teamId, UUID competitorId) {
    relationshipRepository.addRelationship(teamId, competitorId);
  }

  @Override
  public List<UUID> getMemberIds(UUID teamId) {
    return relationshipRepository.getRightsForLeft(teamId);
  }
}
