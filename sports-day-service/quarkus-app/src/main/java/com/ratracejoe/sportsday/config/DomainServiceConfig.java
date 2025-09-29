package com.ratracejoe.sportsday.config;

import com.ratracejoe.sportsday.domain.service.ActivityService;
import com.ratracejoe.sportsday.domain.service.CompetitorService;
import com.ratracejoe.sportsday.domain.service.EventService;
import com.ratracejoe.sportsday.domain.service.ScoreService;
import com.ratracejoe.sportsday.domain.service.TeamService;
import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IMembershipRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IParticipantRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ITeamRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class DomainServiceConfig {
  @Produces
  public IActivityService activityService(
      IActivityRepository repository,
      IAuditLogger auditLogger,
      ISportsDayUserSupplier userSupplier) {
    return new ActivityService(repository, auditLogger, userSupplier);
  }

  @Produces
  public ITeamService teamService(
      ITeamRepository teamRepo,
      ICompetitorRepository competitorRepo,
      IMembershipRepository membershipRepo,
      IAuditLogger auditLogger) {
    return new TeamService(auditLogger, teamRepo, competitorRepo, membershipRepo);
  }

  @Produces
  public ICompetitorService competitorService(ICompetitorRepository competitorRepository) {
    return new CompetitorService(competitorRepository);
  }

  @Produces
  public FinishingOrderService finishingOrderService(
      ICompetitorRepository competitorRepository,
      IFinishingOrderRepository finishingOrderRepository) {

    return new FinishingOrderService(competitorRepository, finishingOrderRepository);
  }

  @Produces
  public TimedFinishingOrderService timedFinishingOrderService(
      ICompetitorRepository competitorRepository,
      ITimedFinishingOrderRepository finishingOrderRepository) {
    return new TimedFinishingOrderService(competitorRepository, finishingOrderRepository);
  }

  @Produces
  public PointScoreService pointScoreService(
      ICompetitorRepository competitorRepository, IPointScoreSheetRepository scoreSheetRepository) {
    return new PointScoreService(competitorRepository, scoreSheetRepository);
  }

  @Produces
  public IScoreService scoreService(
      FinishingOrderService finishingOrderService,
      TimedFinishingOrderService timedFinishingOrderService,
      PointScoreService pointScoreService) {
    return new ScoreService(finishingOrderService, timedFinishingOrderService, pointScoreService);
  }

  @Produces
  public IEventService eventService(
      IEventRepository eventRepository,
      IActivityRepository activityRepository,
      IParticipantRepository participantRepository,
      ICompetitorRepository competitorRepository,
      IScoreService scoreService) {
    return new EventService(
        eventRepository,
        activityRepository,
        participantRepository,
        competitorRepository,
        scoreService);
  }
}
