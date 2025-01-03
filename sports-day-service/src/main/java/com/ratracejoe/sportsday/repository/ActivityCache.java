package com.ratracejoe.sportsday.repository;


import com.ratracejoe.sportsday.repository.cache.CachedActivity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ActivityCache extends CrudRepository<CachedActivity, UUID> {
}
