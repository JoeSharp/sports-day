package com.ratracejoe.sportsday.redis.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.redis.model.CachedActivity;
import com.ratracejoe.sportsday.redis.repository.ActivityRedisCache;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ActivityRepositoryRedisImpl implements IActivityRepository {
  private final ActivityRedisCache cache;

  @Override
  public Activity getById(UUID id) throws NotFoundException {
    return cache
        .findById(id)
        .map(ActivityRepositoryRedisImpl::cacheToDomain)
        .orElseThrow(() -> new NotFoundException(Activity.class, id));
  }

  @Override
  public List<Activity> getAll() {
    return StreamSupport.stream(cache.findAll().spliterator(), true)
        .map(ActivityRepositoryRedisImpl::cacheToDomain)
        .toList();
  }

  @Override
  public void save(Activity item) {
    cache.save(domainToCache(item));
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    cache.deleteById(id);
  }

  private static CachedActivity domainToCache(Activity domain) {
    return new CachedActivity(domain.id(), domain.name(), domain.description());
  }

  private static Activity cacheToDomain(CachedActivity cached) {
    return new Activity(cached.getId(), cached.getName(), cached.getDescription());
  }
}
