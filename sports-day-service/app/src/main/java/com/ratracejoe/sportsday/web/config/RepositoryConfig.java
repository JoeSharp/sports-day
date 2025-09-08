package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.jpa.service.ActivityRepositoryJpaImpl;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import com.ratracejoe.sportsday.redis.repository.ActivityRedisCache;
import com.ratracejoe.sportsday.redis.service.ActivityRepositoryRedisImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
  @Bean
  public IGenericRepository<Activity> activityRepository(
      ActivityJpaRepository repository, ActivityRedisCache cache) {
    IActivityRepository cacheRepo = new ActivityRepositoryRedisImpl(cache);
    return new ActivityRepositoryJpaImpl(repository, cacheRepo);
  }
}
