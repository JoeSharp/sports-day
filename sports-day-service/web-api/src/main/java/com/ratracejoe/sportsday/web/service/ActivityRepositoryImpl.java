package com.ratracejoe.sportsday.web.service;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.web.model.cache.CachedActivity;
import com.ratracejoe.sportsday.web.model.entity.ActivityEntity;
import com.ratracejoe.sportsday.web.repository.ActivityCache;
import com.ratracejoe.sportsday.web.repository.ActivityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityRepositoryImpl implements IActivityRepository {
  private final ActivityRepository activityRepository;
  private final ActivityCache activityCache;

  @PostConstruct
  public void postConstruct() {
    activityRepository.findAll().stream()
        .map(ActivityRepositoryImpl::entityToCache)
        .forEach(activityCache::save);
  }

  @Override
  public Activity getByUuid(UUID id) throws ActivityNotFoundException {
    return activityCache
        .findById(id)
        .map(ActivityRepositoryImpl::cacheToDomain)
        .orElseThrow(() -> new ActivityNotFoundException(id));
  }

  @Override
  public List<Activity> getAll() {
    return StreamSupport.stream(activityCache.findAll().spliterator(), true)
        .map(ActivityRepositoryImpl::cacheToDomain)
        .toList();
  }

  @Override
  @Transactional
  public void saveActivity(Activity activity) {
    ActivityEntity entity = new ActivityEntity(activity.name(), activity.description());
    activityRepository.save(entity);

    activityCache.save(entityToCache(entity));
  }

  @Override
  @Transactional
  public void deleteByUuid(UUID id) throws ActivityNotFoundException {
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
