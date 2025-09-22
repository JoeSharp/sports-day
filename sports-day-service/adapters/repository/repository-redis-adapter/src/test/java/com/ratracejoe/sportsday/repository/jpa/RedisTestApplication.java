package com.ratracejoe.sportsday.repository.jpa;

import com.ratracejoe.sportsday.repository.redis.crud.ActivityRedisCache;
import com.ratracejoe.sportsday.repository.redis.crud.CompetitorRedisCache;
import com.ratracejoe.sportsday.repository.redis.crud.TeamRedisCache;
import com.ratracejoe.sportsday.repository.redis.service.ActivityRepositoryRedisImpl;
import com.ratracejoe.sportsday.repository.redis.service.CompetitorRepositoryRedisImpl;
import com.ratracejoe.sportsday.repository.redis.service.TeamRepositoryRedisImpl;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackageClasses = ActivityRedisCache.class)
public class RedisTestApplication {
  public static final int REDIS_PORT = 6379;

  @Bean
  public ActivityRepositoryRedisImpl activityRepositoryRedis(ActivityRedisCache redisCache) {
    return new ActivityRepositoryRedisImpl(redisCache);
  }

  @Bean
  public CompetitorRepositoryRedisImpl competitorRepository(CompetitorRedisCache redisCache) {
    return new CompetitorRepositoryRedisImpl(redisCache);
  }

  @Bean
  public TeamRepositoryRedisImpl teamRepository(TeamRedisCache redisCache) {
    return new TeamRepositoryRedisImpl(redisCache);
  }
}
