package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.incoming.IActivityFacade;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.outgoing.IAuditLogger;
import java.util.List;
import java.util.UUID;

public class ActivityFacade implements IActivityFacade {
  private final IActivityRepository activityRepository;
  private final IAuditLogger auditLogger;

  public ActivityFacade(IActivityRepository repository, IAuditLogger auditLogger) {
    this.activityRepository = repository;
    this.auditLogger = auditLogger;
  }

  @Override
  public Activity getById(UUID id) throws ActivityNotFoundException {
    try {
      var activity = activityRepository.getById(id);
      auditLogger.sendAudit(String.format("Activity %s read", id));
      return activity;
    } catch (ActivityNotFoundException e) {
      auditLogger.sendAudit(String.format("Failed to read Activity %s", id));
      throw e;
    }
  }

  @Override
  public List<Activity> getAll() {
    auditLogger.sendAudit("Activities were read");
    return activityRepository.getAll();
  }

  @Override
  public Activity createActivity(String name, String description) {
    Activity activity = new Activity(UUID.randomUUID(), name, description);
    activityRepository.save(activity);
    auditLogger.sendAudit(
        String.format("Activity '%s' created with ID: %s", activity.name(), activity.id()));
    return activity;
  }

  @Override
  public void deleteByUuid(UUID id) throws ActivityNotFoundException {
    try {
      activityRepository.deleteById(id);
    } catch (ActivityNotFoundException e) {
      auditLogger.sendAudit(String.format("Failed to delete Activity %s", id));
      throw e;
    }
    auditLogger.sendAudit(String.format("Activity %s deleted", id));
  }
}
