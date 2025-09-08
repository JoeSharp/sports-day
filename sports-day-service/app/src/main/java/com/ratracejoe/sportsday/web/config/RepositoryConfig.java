package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.domain.repository.CachedRepository;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.jpa.repository.CompetitorJpaRepository;
import com.ratracejoe.sportsday.jpa.repository.TeamJpaRepository;
import com.ratracejoe.sportsday.jpa.service.ActivityRepositoryJpaImpl;
import com.ratracejoe.sportsday.jpa.service.CompetitorRepositoryJpaImpl;
import com.ratracejoe.sportsday.jpa.service.TeamRepositoryJpaImpl;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import com.ratracejoe.sportsday.redis.repository.ActivityRedisCache;
import com.ratracejoe.sportsday.redis.repository.CompetitorRedisCache;
import com.ratracejoe.sportsday.redis.repository.TeamRedisCache;
import com.ratracejoe.sportsday.redis.service.ActivityRepositoryRedisImpl;
import com.ratracejoe.sportsday.redis.service.CompetitorRepositoryRedisImpl;
import com.ratracejoe.sportsday.redis.service.TeamRepositoryRedisImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
  @Bean
  public IGenericRepository<Activity> activityRepository(
      ActivityJpaRepository repository, ActivityRedisCache cache) {
    IGenericRepository<Activity> jpaRepo = new ActivityRepositoryJpaImpl(repository);
    IGenericRepository<Activity> cacheRepo = new ActivityRepositoryRedisImpl(cache);
    return new CachedRepository<>(jpaRepo, cacheRepo);
  }

  @Bean
  public IGenericRepository<Competitor> competitorRepository(
      CompetitorJpaRepository repository, CompetitorRedisCache cache) {
    IGenericRepository<Competitor> jpaRepo = new CompetitorRepositoryJpaImpl(repository);
    IGenericRepository<Competitor> cacheRepo = new CompetitorRepositoryRedisImpl(cache);
    return new CachedRepository<>(jpaRepo, cacheRepo);
  }

  @Bean
  public IGenericRepository<Team> teamRepository(
      TeamJpaRepository repository, TeamRedisCache cache) {
    IGenericRepository<Team> jpaRepo = new TeamRepositoryJpaImpl(repository);
    IGenericRepository<Team> cacheRepo = new TeamRepositoryRedisImpl(cache);
    return new CachedRepository<>(jpaRepo, cacheRepo);
  }
}
