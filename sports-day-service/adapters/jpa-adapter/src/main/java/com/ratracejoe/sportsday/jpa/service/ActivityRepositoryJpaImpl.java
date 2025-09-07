package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.jpa.model.ActivityEntity;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.redis.model.CachedActivity;
import com.ratracejoe.sportsday.redis.repository.ActivityRedisCache;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityRepositoryJpaImpl implements IActivityRepository {
  private final ActivityJpaRepository activityRepository;
  private final ActivityRedisCache activityCache;

  @PostConstruct
  public void postConstruct() {
    activityRepository.findAll().stream()
        .map(ActivityRepositoryJpaImpl::entityToCache)
        .forEach(activityCache::save);
  }

  @Override
  public Activity getById(UUID id) throws ActivityNotFoundException {
    return activityCache
        .findById(id)
        .map(ActivityRepositoryJpaImpl::cacheToDomain)
        .orElseThrow(() -> new ActivityNotFoundException(id));
  }

  @Override
  public List<Activity> getAll() {
    return StreamSupport.stream(activityCache.findAll().spliterator(), true)
        .map(ActivityRepositoryJpaImpl::cacheToDomain)
        .toList();
  }

  @Override
  @Transactional
  public void save(Activity activity) {
    ActivityEntity entity =
        new ActivityEntity(activity.id(), activity.name(), activity.description());
    activityRepository.save(entity);

    activityCache.save(entityToCache(entity));
  }

  @Override
  @Transactional
  public void deleteById(UUID id) throws ActivityNotFoundException {
    if (!activityRepository.existsById(id)) {
      throw new ActivityNotFoundException(id);
    }
    activityCache.deleteById(id);
    activityRepository.deleteById(id);
  }

  private static CachedActivity entityToCache(ActivityEntity entity) {
    return new CachedActivity(entity.getId(), entity.getName(), entity.getDescription());
  }

  private static Activity cacheToDomain(CachedActivity cached) {
    return new Activity(cached.getId(), cached.getName(), cached.getDescription());
  }
}
