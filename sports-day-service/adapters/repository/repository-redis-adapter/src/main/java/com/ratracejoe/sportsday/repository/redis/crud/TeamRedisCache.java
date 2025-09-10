package com.ratracejoe.sportsday.repository.redis.crud;

import com.ratracejoe.sportsday.repository.redis.entity.CachedTeam;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface TeamRedisCache extends CrudRepository<CachedTeam, UUID> {}
