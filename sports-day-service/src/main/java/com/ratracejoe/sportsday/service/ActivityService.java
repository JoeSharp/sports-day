package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.repository.ActivityCache;
import com.ratracejoe.sportsday.repository.ActivityRepository;
import com.ratracejoe.sportsday.repository.cache.ActivityTemp;
import com.ratracejoe.sportsday.repository.entity.ActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    private static ActivityTemp mapToCache(ActivityEntity entity) {
        return new ActivityTemp(entity.getId(), entity.getName(), entity.getDescription());
    }

    public ActivityDTO createActivity(ActivityDTO dto) {
        ActivityEntity entity = new ActivityEntity(dto.name(), dto.description());
        activityRepository.save(entity);

        activityCache.save(mapToCache(entity));

        return mapToDTO(entity);
    }
}
