package com.ratracejoe.sportsday.repository;

import com.ratracejoe.sportsday.model.cache.CachedActivity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ActivityCache extends CrudRepository<CachedActivity, UUID> {}
