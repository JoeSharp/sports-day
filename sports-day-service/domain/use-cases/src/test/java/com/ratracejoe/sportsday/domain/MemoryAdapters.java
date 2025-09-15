package com.ratracejoe.sportsday.domain;

import com.ratracejoe.sportsday.domain.service.*;
import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import com.ratracejoe.sportsday.repository.memory.MemoryActivityRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryCompetitorRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryEventRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryMembershipRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryParticipantRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryTeamRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryPointScoreSheetRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryTimedFinishingOrderRepository;

public class MemoryAdapters {
  private final MemoryAuditLogger auditLogger;
  private final ActivityService activityService;
  private final TeamService teamService;
  private final CompetitorService competitorService;
  private final EventService eventService;
  private final ScoreService scoreService;

  public MemoryAdapters() {
    auditLogger = new MemoryAuditLogger();
    MemoryActivityRepository activityRepository = new MemoryActivityRepository();
    MemoryTeamRepository teamRepository = new MemoryTeamRepository();
    MemoryCompetitorRepository competitorRepository = new MemoryCompetitorRepository();
    MemoryEventRepository eventRepository = new MemoryEventRepository();
    MemoryPointScoreSheetRepository pointScoreSheetRepository =
        new MemoryPointScoreSheetRepository();
    MemoryTimedFinishingOrderRepository timedFinishingOrderRepository =
        new MemoryTimedFinishingOrderRepository();
    MemoryFinishingOrderRepository finishingOrderRepository = new MemoryFinishingOrderRepository();
    MemoryMembershipRepository membershipRepository =
        new MemoryMembershipRepository(teamRepository, competitorRepository);
    MemoryParticipantRepository participantRepository =
        new MemoryParticipantRepository(eventRepository, competitorRepository);
    teamService =
        new TeamService(auditLogger, teamRepository, competitorRepository, membershipRepository);
    competitorService = new CompetitorService(competitorRepository);
    activityService = new ActivityService(activityRepository, auditLogger);
    FinishingOrderService finishingOrderService =
        new FinishingOrderService(competitorRepository, finishingOrderRepository);
    TimedFinishingOrderService timedFinishingOrderService =
        new TimedFinishingOrderService(competitorRepository, timedFinishingOrderRepository);
    PointScoreService pointScoreService =
        new PointScoreService(competitorRepository, pointScoreSheetRepository);
    scoreService =
        new ScoreService(finishingOrderService, timedFinishingOrderService, pointScoreService);
    eventService =
        new EventService(
            eventRepository,
            activityRepository,
            participantRepository,
            competitorRepository,
            scoreService);
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

  public ScoreService scoreService() {
    return scoreService;
  }

  public MemoryAuditLogger auditLogger() {
    return auditLogger;
  }
}
