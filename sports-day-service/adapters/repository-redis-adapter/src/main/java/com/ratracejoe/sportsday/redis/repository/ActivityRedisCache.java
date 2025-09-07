package com.ratracejoe.sportsday.redis.repository;

import com.ratracejoe.sportsday.redis.model.CachedActivity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRedisCache extends CrudRepository<CachedActivity, UUID> {}
