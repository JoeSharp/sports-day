package com.ratracejoe.sportsday.repository.redis.crud;

import com.ratracejoe.sportsday.repository.redis.entity.CachedActivity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRedisCache extends CrudRepository<CachedActivity, UUID> {}
