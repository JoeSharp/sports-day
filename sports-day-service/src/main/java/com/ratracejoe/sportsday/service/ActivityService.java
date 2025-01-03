package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.repository.ActivityCache;
import com.ratracejoe.sportsday.repository.ActivityRepository;
import com.ratracejoe.sportsday.repository.cache.CachedActivity;
import com.ratracejoe.sportsday.repository.entity.ActivityEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final AuditService auditService;
    private final ActivityRepository activityRepository;
    private final ActivityCache activityCache;

    public List<ActivityDTO> getActivities() {
        auditService.activitiesRead();
        return activityRepository.findAll().stream()
                .map(ActivityService::mapToDTO)
                .toList();
    }

    private static ActivityDTO mapToDTO(ActivityEntity entity) {
        return new ActivityDTO(entity.getId(), entity.getName(), entity.getDescription());
    }

    private static CachedActivity mapToCache(ActivityEntity entity) {
        return new CachedActivity(entity.getId(), entity.getName(), entity.getDescription());
    }

    @Transactional
    public ActivityDTO createActivity(ActivityDTO dto) {
        ActivityEntity entity = new ActivityEntity(dto.name(), dto.description());
        activityRepository.save(entity);
        auditService.activityCreated(entity);

        activityCache.save(mapToCache(entity));

        return mapToDTO(entity);
    }
}
