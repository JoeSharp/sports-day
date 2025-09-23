package com.ratracejoe.sportsday.domain;

import com.ratracejoe.sportsday.auth.MemorySportsDayUserSupplier;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.domain.service.*;
import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import com.ratracejoe.sportsday.repository.memory.MemoryActivityRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryCompetitorRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryEventRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryMembershipRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryParticipantRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryTeamRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryPointScoreSheetRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryTimedFinishingOrderRepository;
import java.util.List;

public class MemoryAdapters {
  private final MemorySportsDayUserSupplier userSupplier;
  private final MemoryAuditLogger auditLogger;
  private final IActivityService activityService;
  private final ITeamService teamService;
  private final ICompetitorService competitorService;
  private final IEventService eventService;
  private final IScoreService scoreService;

  public MemoryAdapters() {
    auditLogger = new MemoryAuditLogger();
    userSupplier = new MemorySportsDayUserSupplier();
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
    activityService = new ActivityService(activityRepository, auditLogger, userSupplier);
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

  public void setCurrentUser(SportsDayUser currentUser) {
    userSupplier.setCurrentUser(currentUser);
  }

  public List<String> getAuditMessages() {
    return auditLogger.getMessages();
  }

  public IActivityService activityService() {
    return activityService;
  }

  public ICompetitorService competitorService() {
    return competitorService;
  }

  public ITeamService teamService() {
    return teamService;
  }

  public IEventService eventService() {
    return eventService;
  }

  public IScoreService scoreService() {
    return scoreService;
  }

  public ISportsDayUserSupplier userSupplier() {
    return userSupplier;
  }

  public IAuditLogger auditLogger() {
    return auditLogger;
  }
}
