package com.ratracejoe.sportsday.web.repository;

import com.ratracejoe.sportsday.web.model.cache.CachedActivity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

public interface ActivityCache extends CrudRepository<CachedActivity, UUID> {}
