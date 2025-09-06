package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.jpa.repository.ActivityRedisCache;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories(basePackageClasses = ActivityRedisCache.class)
public class RedisAdapterConfig {}
