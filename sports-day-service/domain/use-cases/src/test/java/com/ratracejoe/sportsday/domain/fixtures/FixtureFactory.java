package com.ratracejoe.sportsday.domain.fixtures;

import com.ratracejoe.sportsday.domain.outgoing.*;
import com.ratracejoe.sportsday.domain.service.ActivityService;
import com.ratracejoe.sportsday.domain.service.CompetitorService;
import com.ratracejoe.sportsday.domain.service.EventService;
import com.ratracejoe.sportsday.domain.service.TeamService;

public class FixtureFactory {
  private final MemoryAuditLogger auditLogger;
  private final ActivityService activityFacade;
  private final TeamService teamFacade;
  private final CompetitorService competitorFacade;
  private final EventService eventFacade;

  public FixtureFactory() {
    auditLogger = new MemoryAuditLogger();
    MemoryActivityRepository activityRepository = new MemoryActivityRepository();
    MemoryTeamRepository teamRepository = new MemoryTeamRepository();
    MemoryCompetitorRepository competitorRepository = new MemoryCompetitorRepository();
    MemoryEventRepository eventRepository = new MemoryEventRepository();
    MemoryMembershipRepository membershipRepository =
        new MemoryMembershipRepository(teamRepository, competitorRepository);
    MemoryParticipantRepository participantRepository =
        new MemoryParticipantRepository(eventRepository, competitorRepository);
    teamFacade =
        new TeamService(auditLogger, teamRepository, competitorRepository, membershipRepository);
    competitorFacade = new CompetitorService(competitorRepository);
    activityFacade = new ActivityService(activityRepository, auditLogger);
    eventFacade =
        new EventService(
            eventRepository, activityRepository, participantRepository, competitorRepository);
  }

  public ActivityService activityFacade() {
    return activityFacade;
  }

  public CompetitorService competitorFacade() {
    return competitorFacade;
  }

  public TeamService teamFacade() {
    return teamFacade;
  }

  public EventService eventFacade() {
    return eventFacade;
  }

  public MemoryAuditLogger auditLogger() {
    return auditLogger;
  }
}
