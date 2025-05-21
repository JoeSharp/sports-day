package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.model.cache.CachedActivity;
import com.ratracejoe.sportsday.model.entity.ActivityEntity;
import com.ratracejoe.sportsday.model.rest.ActivityDTO;
import com.ratracejoe.sportsday.repository.ActivityCache;
import com.ratracejoe.sportsday.repository.ActivityRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivityService {
  private final AuditService auditService;
  private final ActivityRepository activityRepository;
  private final ActivityCache activityCache;

  @PostConstruct
  public void postConstruct() {
    activityRepository.findAll().stream()
        .map(ActivityService::entityToCache)
        .forEach(activityCache::save);
  }

  public ActivityDTO getActivity(UUID id) throws ActivityNotFoundException {
    auditService.activityRead(id);
    return activityCache
        .findById(id)
        .map(ActivityService::cacheToDTO)
        .orElseThrow(() -> new ActivityNotFoundException(id));
  }

  public List<ActivityDTO> getActivities() {
    auditService.activitiesRead();

    return StreamSupport.stream(activityCache.findAll().spliterator(), true)
        .map(ActivityService::cacheToDTO)
        .toList();
  }

  @Transactional
  public ActivityDTO createActivity(ActivityDTO dto) {
    ActivityEntity entity = new ActivityEntity(dto.name(), dto.description());
    activityRepository.save(entity);
    auditService.activityCreated(entity);

    activityCache.save(entityToCache(entity));

    return entityToDTO(entity);
  }

  @Transactional
  public void deleteActivity(UUID id) throws ActivityNotFoundException {
    if (!activityRepository.existsById(id)) {
      auditService.activityDeletionFailed();
      throw new ActivityNotFoundException(id);
    }
    auditService.activityDeleted(id);
    activityCache.deleteById(id);
    activityRepository.deleteById(id);
  }

  private static ActivityDTO entityToDTO(ActivityEntity entity) {
    return new ActivityDTO(entity.getId(), entity.getName(), entity.getDescription());
  }

  private static CachedActivity entityToCache(ActivityEntity entity) {
    return new CachedActivity(entity.getId(), entity.getName(), entity.getDescription());
  }

  private static ActivityDTO cacheToDTO(CachedActivity cached) {
    return new ActivityDTO(cached.getId(), cached.getName(), cached.getDescription());
  }
}
