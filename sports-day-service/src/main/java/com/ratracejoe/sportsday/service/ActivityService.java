package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.repository.ActivityRepository;
import com.ratracejoe.sportsday.repository.entity.ActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public List<ActivityDTO> getActivities() {
        return activityRepository.findAll().stream()
                .map(ActivityService::mapToDTO)
                .toList();
    }

    private static ActivityDTO mapToDTO(ActivityEntity entity) {
        return new ActivityDTO(entity.getId(), entity.getName(), entity.getDescription());
    }
}
