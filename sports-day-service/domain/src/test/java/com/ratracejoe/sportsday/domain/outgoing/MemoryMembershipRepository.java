package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.outgoing.ICompetitorRepository;
import com.ratracejoe.sportsday.outgoing.IMembershipRepository;
import com.ratracejoe.sportsday.outgoing.ITeamRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryMembershipRepository implements IMembershipRepository {
  record Membership(UUID teamId, UUID competitorId) {}

  private final List<Membership> membershipList;

  private final ITeamRepository teamRepository;
  private final ICompetitorRepository competitorRepository;

  public MemoryMembershipRepository(
      ITeamRepository teamRepository, ICompetitorRepository competitorRepository) {
    this.teamRepository = teamRepository;
    this.competitorRepository = competitorRepository;
    this.membershipList = new ArrayList<>();
  }

  @Override
  public void addMembership(UUID teamId, UUID competitorId) {
    Team team = teamRepository.getById(teamId);
    Competitor competitor = competitorRepository.getById(competitorId);
    membershipList.add(new Membership(team.id(), competitor.id()));
  }

  @Override
  public List<UUID> getMemberIds(UUID teamId) {
    return membershipList.stream()
        .filter(m -> m.teamId().equals(teamId))
        .map(Membership::competitorId)
        .toList();
  }
}
