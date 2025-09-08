package com.ratracejoe.sportsday.redis.repository;

import com.ratracejoe.sportsday.redis.model.CachedCompetitor;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CompetitorRedisCache extends CrudRepository<CachedCompetitor, UUID> {}
