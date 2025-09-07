package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.jpa.model.ActivityEntity;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityRepositoryJpaImpl implements IActivityRepository {
  private final ActivityJpaRepository activityRepository;
  private final IActivityRepository activityCache;

  @PostConstruct
  public void postConstruct() {
    activityRepository.findAll().stream()
        .map(ActivityRepositoryJpaImpl::entityToDomain)
        .forEach(activityCache::save);
  }

  @Override
  public Activity getById(UUID id) throws ActivityNotFoundException {
    return activityCache.getById(id);
  }

  @Override
  public List<Activity> getAll() {
    return activityCache.getAll();
  }

  @Override
  @Transactional
  public void save(Activity activity) {
    ActivityEntity entity = domainToEntity(activity);
    activityRepository.save(entity);
    activityCache.save(activity);
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

  private static ActivityEntity domainToEntity(Activity activity) {
    return new ActivityEntity(activity.id(), activity.name(), activity.description());
  }

  private static Activity entityToDomain(ActivityEntity entity) {
    return new Activity(entity.getId(), entity.getName(), entity.getDescription());
  }
}
