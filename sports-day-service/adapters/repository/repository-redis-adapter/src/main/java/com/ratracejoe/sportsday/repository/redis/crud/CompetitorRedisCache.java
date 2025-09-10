package com.ratracejoe.sportsday.repository.redis.crud;

import com.ratracejoe.sportsday.repository.redis.entity.CachedCompetitor;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface CompetitorRedisCache extends CrudRepository<CachedCompetitor, UUID> {}
