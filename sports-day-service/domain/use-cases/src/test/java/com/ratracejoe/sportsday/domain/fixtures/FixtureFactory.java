package com.ratracejoe.sportsday.domain.fixtures;

import com.ratracejoe.sportsday.domain.facade.ActivityFacade;
import com.ratracejoe.sportsday.domain.facade.CompetitorFacade;
import com.ratracejoe.sportsday.domain.facade.EventFacade;
import com.ratracejoe.sportsday.domain.facade.TeamFacade;
import com.ratracejoe.sportsday.domain.outgoing.*;

public class FixtureFactory {
  private final MemoryAuditLogger auditLogger;
  private final ActivityFacade activityFacade;
  private final TeamFacade teamFacade;
  private final CompetitorFacade competitorFacade;
  private final EventFacade eventFacade;

  public FixtureFactory() {
    auditLogger = new MemoryAuditLogger();
    MemoryActivityRepository activityRepository = new MemoryActivityRepository();
    MemoryTeamRepository teamRepository = new MemoryTeamRepository();
    MemoryCompetitorRepository competitorRepository = new MemoryCompetitorRepository();
    MemoryEventRepository eventRepository = new MemoryEventRepository();
    MemoryMembershipRepository membershipRepository =
        new MemoryMembershipRepository(teamRepository, competitorRepository);
    teamFacade =
        new TeamFacade(auditLogger, teamRepository, competitorRepository, membershipRepository);
    competitorFacade = new CompetitorFacade(competitorRepository);
    activityFacade = new ActivityFacade(activityRepository, auditLogger);
    eventFacade = new EventFacade(eventRepository, activityRepository);
  }

  public ActivityFacade activityFacade() {
    return activityFacade;
  }

  public CompetitorFacade competitorFacade() {
    return competitorFacade;
  }

  public TeamFacade teamFacade() {
    return teamFacade;
  }

  public EventFacade eventFacade() {
    return eventFacade;
  }

  public MemoryAuditLogger auditLogger() {
    return auditLogger;
  }
}
