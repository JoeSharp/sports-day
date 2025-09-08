package com.ratracejoe.sportsday.redis.repository;

import com.ratracejoe.sportsday.redis.model.CachedTeam;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TeamRedisCache extends CrudRepository<CachedTeam, UUID> {}
