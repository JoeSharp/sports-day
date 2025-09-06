package com.ratracejoe.sportsday.jpa.repository;

import com.ratracejoe.sportsday.jpa.model.cache.CachedActivity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ActivityRedisCache extends CrudRepository<CachedActivity, UUID> {}
