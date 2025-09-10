package com.ratracejoe.sportsday.domain;

import com.ratracejoe.sportsday.domain.service.ActivityService;
import com.ratracejoe.sportsday.domain.service.CompetitorService;
import com.ratracejoe.sportsday.domain.service.EventService;
import com.ratracejoe.sportsday.domain.service.TeamService;
import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import com.ratracejoe.sportsday.repository.memory.MemoryActivityRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryCompetitorRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryEventRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryMembershipRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryParticipantRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryTeamRepository;

public class MemoryAdapters {
  private final MemoryAuditLogger auditLogger;
  private final ActivityService activityService;
  private final TeamService teamService;
  private final CompetitorService competitorService;
  private final EventService eventService;

  public MemoryAdapters() {
    auditLogger = new MemoryAuditLogger();
    MemoryActivityRepository activityRepository = new MemoryActivityRepository();
    MemoryTeamRepository teamRepository = new MemoryTeamRepository();
    MemoryCompetitorRepository competitorRepository = new MemoryCompetitorRepository();
    MemoryEventRepository eventRepository = new MemoryEventRepository();
    MemoryMembershipRepository membershipRepository =
        new MemoryMembershipRepository(teamRepository, competitorRepository);
    MemoryParticipantRepository participantRepository =
        new MemoryParticipantRepository(eventRepository, competitorRepository);
    teamService =
        new TeamService(auditLogger, teamRepository, competitorRepository, membershipRepository);
    competitorService = new CompetitorService(competitorRepository);
    activityService = new ActivityService(activityRepository, auditLogger);
    eventService =
        new EventService(
            eventRepository, activityRepository, participantRepository, competitorRepository);
  }

  public ActivityService activityService() {
    return activityService;
  }

  public CompetitorService competitorService() {
    return competitorService;
  }

  public TeamService teamService() {
    return teamService;
  }

  public EventService eventService() {
    return eventService;
  }

  public MemoryAuditLogger auditLogger() {
    return auditLogger;
  }
}
