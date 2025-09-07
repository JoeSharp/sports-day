package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.jpa.model.ActivityEntity;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.jpa.service.ActivityRepositoryJpaImpl;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.redis.repository.ActivityRedisCache;
import com.ratracejoe.sportsday.redis.service.ActivityRepositoryRedisImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = ActivityJpaRepository.class)
@EntityScan(basePackageClasses = ActivityEntity.class)
public class JpaAdapterConfig {
  @Bean
  public IActivityRepository activityRepository(
      ActivityJpaRepository repository, ActivityRedisCache cache) {
    IActivityRepository cacheRepo = new ActivityRepositoryRedisImpl(cache);
    return new ActivityRepositoryJpaImpl(repository, cacheRepo);
  }
}
