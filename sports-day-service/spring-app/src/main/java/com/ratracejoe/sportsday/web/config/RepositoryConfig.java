package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.ports.outgoing.repository.*;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.cache.ActivityCachedRepository;
import com.ratracejoe.sportsday.repository.cache.CompetitorCachedRepository;
import com.ratracejoe.sportsday.repository.cache.TeamCachedRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.IActivityJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.ICompetitorJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.IEventJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.ITeamJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.score.IFinishingOrderJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.score.IPointScoreSheetJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.score.ITimedFinishingOrderJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.service.*;
import com.ratracejoe.sportsday.repository.jpa.service.score.FinishingOrderRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.score.PointScoreRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.score.TimedFinishingOrderRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.redis.crud.ActivityRedisCache;
import com.ratracejoe.sportsday.repository.redis.crud.CompetitorRedisCache;
import com.ratracejoe.sportsday.repository.redis.crud.TeamRedisCache;
import com.ratracejoe.sportsday.repository.redis.service.ActivityRepositoryRedisImpl;
import com.ratracejoe.sportsday.repository.redis.service.CompetitorRepositoryRedisImpl;
import com.ratracejoe.sportsday.repository.redis.service.TeamRepositoryRedisImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class RepositoryConfig {
  @Bean
  public IActivityRepository activityRepository(
      IActivityJpaRepository repository, ActivityRedisCache cache) {
    IActivityRepository jpaRepo = new ActivityRepositoryJpaImpl(repository);
    IActivityRepository cacheRepo = new ActivityRepositoryRedisImpl(cache);
    return new ActivityCachedRepository(jpaRepo, cacheRepo);
  }

  @Bean
  public ICompetitorRepository competitorRepository(
      ICompetitorJpaRepository repository, CompetitorRedisCache cache) {
    ICompetitorRepository jpaRepo = new CompetitorRepositoryJpaImpl(repository);
    ICompetitorRepository cacheRepo = new CompetitorRepositoryRedisImpl(cache);
    return new CompetitorCachedRepository(jpaRepo, cacheRepo);
  }

  @Bean
  public ITeamRepository teamRepository(ITeamJpaRepository jpa, TeamRedisCache cache) {
    ITeamRepository jpaRepo = new TeamRepositoryJpaImpl(jpa);
    ITeamRepository cacheRepo = new TeamRepositoryRedisImpl(cache);
    return new TeamCachedRepository(jpaRepo, cacheRepo);
  }

  @Bean
  public IMembershipRepository membershipRepository(JdbcTemplate jdbcTemplate) {
    return new MembershipRepositoryJpaImpl(jdbcTemplate);
  }

  @Bean
  public IParticipantRepository participantRepository(JdbcTemplate jdbcTemplate) {
    return new ParticipantRepositoryJpaImpl(jdbcTemplate);
  }

  @Bean
  public IEventRepository eventRepository(IEventJpaRepository repository) {
    return new EventRepositoryJpaImpl(repository);
  }

  @Bean
  public ITimedFinishingOrderRepository timedFinishingOrderRepository(
      ITimedFinishingOrderJpaRepository jpaRepository) {
    return new TimedFinishingOrderRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public IFinishingOrderRepository finishingOrderRepository(
      IFinishingOrderJpaRepository jpaRepository) {
    return new FinishingOrderRepositoryJpaImpl(jpaRepository);
  }

  @Bean
  public IPointScoreSheetRepository scoreSheetRepository(
      IPointScoreSheetJpaRepository jpaRepository) {
    return new PointScoreRepositoryJpaImpl(jpaRepository);
  }
}
