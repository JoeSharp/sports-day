package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.repository.redis.crud.ActivityRedisCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(basePackageClasses = ActivityRedisCache.class)
@Configuration
public class RedisAdapterConfig {}
