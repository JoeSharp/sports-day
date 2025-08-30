package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.incoming.IActivityFacade;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;

import java.util.List;
import java.util.UUID;

public class ActivityFacade implements IActivityFacade {
    private final IActivityRepository activityRepository;
    private final AuditService auditService;

    public ActivityFacade(IActivityRepository repository,
                          AuditService auditService) {
        this.activityRepository = repository;
        this.auditService = auditService;
    }

    @Override
    public Activity getByUuid(UUID id) throws ActivityNotFoundException {
        auditService.activityRead(id);
        return activityRepository.getByUuid(id);
    }

    @Override
    public List<Activity> getAll() {
        auditService.activitiesRead();
        return activityRepository.getAll();
    }

    @Override
    public Activity createActivity(String name, String description) {
        Activity activity = new Activity(UUID.randomUUID(), name, description);
        activityRepository.saveActivity(activity);
        auditService.activityCreated(activity);
        return activity;
    }

    @Override
    public void deleteByUuid(UUID id) throws ActivityNotFoundException {
        try {
            activityRepository.deleteByUuid(id);
        } catch (ActivityNotFoundException e) {
            auditService.activityDeletionFailed();
            throw e;
        }
        auditService.activityDeleted(id);
    }
}
