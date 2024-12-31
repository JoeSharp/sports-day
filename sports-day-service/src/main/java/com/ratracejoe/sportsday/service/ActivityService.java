package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.repository.ActivityRepository;
import com.ratracejoe.sportsday.repository.entity.ActivityEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;

    public List<String> getActivities() {
        return activityRepository.findAll().stream().map(ActivityEntity::getName).toList();
    }
}
