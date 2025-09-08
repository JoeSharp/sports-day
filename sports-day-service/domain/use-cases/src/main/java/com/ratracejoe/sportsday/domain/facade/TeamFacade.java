package com.ratracejoe.sportsday.domain.facade;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.incoming.ITeamFacade;
import com.ratracejoe.sportsday.ports.outgoing.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.IMembershipRepository;
import com.ratracejoe.sportsday.ports.outgoing.ITeamRepository;
import java.util.List;
import java.util.UUID;

public class TeamFacade implements ITeamFacade {
  private final IAuditLogger auditLogger;
  private final ITeamRepository teamRepository;
  private final ICompetitorRepository competitorRepository;
  private final IMembershipRepository membershipRepository;

  public TeamFacade(
      IAuditLogger auditLogger,
      ITeamRepository teamRepository,
      ICompetitorRepository competitorRepository,
      IMembershipRepository membershipRepository) {
    this.auditLogger = auditLogger;
    this.teamRepository = teamRepository;
    this.competitorRepository = competitorRepository;
    this.membershipRepository = membershipRepository;
  }

  @Override
  public Team createTeam(String name) {
    Team team = new Team(UUID.randomUUID(), name);
    teamRepository.save(team);
    auditLogger.sendAudit(String.format("Team '%s' created with ID: %s", name, team.id()));
    return team;
  }

  @Override
  public Team getById(UUID id) throws NotFoundException {
    return teamRepository.getById(id);
  }

  @Override
  public List<Competitor> getMembers(UUID teamId) throws NotFoundException {
    Team team = teamRepository.getById(teamId);
    List<UUID> memberIds = membershipRepository.getMemberIds(team.id());
    return memberIds.stream().map(competitorRepository::getById).toList();
  }

  @Override
  public void registerMember(UUID teamId, UUID competitorId) throws NotFoundException {
    Team team = teamRepository.getById(teamId);
    Competitor competitor = competitorRepository.getById(competitorId);
    membershipRepository.addMembership(team.id(), competitor.id());
  }
}
