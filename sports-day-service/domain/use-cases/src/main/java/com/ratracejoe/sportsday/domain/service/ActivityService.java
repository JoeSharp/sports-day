package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import java.util.List;
import java.util.UUID;

public class ActivityService implements IActivityService {
  private final IActivityRepository activityRepository;
  private final IAuditLogger auditLogger;
  private final ISportsDayUserSupplier userSupplier;

  public ActivityService(
      IActivityRepository repository,
      IAuditLogger auditLogger,
      ISportsDayUserSupplier userSupplier) {
    this.activityRepository = repository;
    this.auditLogger = auditLogger;
    this.userSupplier = userSupplier;
  }

  @Override
  public Activity getById(UUID id) throws NotFoundException {
    try {
      var activity = activityRepository.getById(id);
      auditLogger.sendAudit(String.format("Activity %s read", id));
      return activity;
    } catch (NotFoundException e) {
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
    userSupplier.userHasRole(SportsDayRole.PE_TEACHER);
    Activity activity = new Activity(UUID.randomUUID(), name, description);
    activityRepository.save(activity);
    auditLogger.sendAudit(
        String.format("Activity '%s' created with ID: %s", activity.name(), activity.id()));
    return activity;
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    try {
      activityRepository.deleteById(id);
    } catch (NotFoundException e) {
      auditLogger.sendAudit(String.format("Failed to delete Activity %s", id));
      throw e;
    }
    auditLogger.sendAudit(String.format("Activity %s deleted", id));
  }
}
