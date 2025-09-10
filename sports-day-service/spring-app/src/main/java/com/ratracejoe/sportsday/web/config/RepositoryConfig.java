package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IMembershipRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ITeamRepository;
import com.ratracejoe.sportsday.repository.cache.ActivityCachedRepository;
import com.ratracejoe.sportsday.repository.cache.CompetitorCachedRepository;
import com.ratracejoe.sportsday.repository.cache.TeamCachedRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.CompetitorJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.repository.TeamJpaRepository;
import com.ratracejoe.sportsday.repository.jpa.service.ActivityRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.CompetitorRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.MembershipRepositoryJpaImpl;
import com.ratracejoe.sportsday.repository.jpa.service.TeamRepositoryJpaImpl;
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
      ActivityJpaRepository repository, ActivityRedisCache cache) {
    IActivityRepository jpaRepo = new ActivityRepositoryJpaImpl(repository);
    IActivityRepository cacheRepo = new ActivityRepositoryRedisImpl(cache);
    return new ActivityCachedRepository(jpaRepo, cacheRepo);
  }

  @Bean
  public ICompetitorRepository competitorRepository(
      CompetitorJpaRepository repository, CompetitorRedisCache cache) {
    ICompetitorRepository jpaRepo = new CompetitorRepositoryJpaImpl(repository);
    ICompetitorRepository cacheRepo = new CompetitorRepositoryRedisImpl(cache);
    return new CompetitorCachedRepository(jpaRepo, cacheRepo);
  }

  @Bean
  public ITeamRepository teamRepository(TeamJpaRepository jpa, TeamRedisCache cache) {
    ITeamRepository jpaRepo = new TeamRepositoryJpaImpl(jpa);
    ITeamRepository cacheRepo = new TeamRepositoryRedisImpl(cache);
    return new TeamCachedRepository(jpaRepo, cacheRepo);
  }

  @Bean
  public IMembershipRepository membershipRepository(JdbcTemplate jdbcTemplate) {
    return new MembershipRepositoryJpaImpl(jdbcTemplate);
  }
}
