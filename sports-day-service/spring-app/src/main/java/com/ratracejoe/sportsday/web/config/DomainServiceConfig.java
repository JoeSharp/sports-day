package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.service.*;
import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.*;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.repository.*;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {
  @Bean
  public IActivityService activityService(
      IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityService(repository, auditLogger);
  }

  @Bean
  public ITeamService teamService(
      ITeamRepository teamRepo,
      ICompetitorRepository competitorRepo,
      IMembershipRepository membershipRepo,
      IAuditLogger auditLogger) {
    return new TeamService(auditLogger, teamRepo, competitorRepo, membershipRepo);
  }

  @Bean
  public ICompetitorService competitorService(ICompetitorRepository competitorRepository) {
    return new CompetitorService(competitorRepository);
  }

  @Bean
  public FinishingOrderService finishingOrderService(
      ICompetitorRepository competitorRepository,
      IFinishingOrderRepository finishingOrderRepository) {

    return new FinishingOrderService(competitorRepository, finishingOrderRepository);
  }

  @Bean
  public TimedFinishingOrderService timedFinishingOrderService(
      ICompetitorRepository competitorRepository,
      ITimedFinishingOrderRepository finishingOrderRepository) {
    return new TimedFinishingOrderService(competitorRepository, finishingOrderRepository);
  }

  @Bean
  public PointScoreService pointScoreService(
      ICompetitorRepository competitorRepository, IPointScoreSheetRepository scoreSheetRepository) {
    return new PointScoreService(competitorRepository, scoreSheetRepository);
  }

  @Bean
  public IScoreService scoreService(
      FinishingOrderService finishingOrderService,
      TimedFinishingOrderService timedFinishingOrderService,
      PointScoreService pointScoreService) {
    return new ScoreService(finishingOrderService, timedFinishingOrderService, pointScoreService);
  }

  @Bean
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
