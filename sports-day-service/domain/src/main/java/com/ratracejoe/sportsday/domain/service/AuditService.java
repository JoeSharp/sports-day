package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.outgoing.IAuditLogger;

import java.util.UUID;

public class AuditService {

  private final IAuditLogger auditLogger;

  public AuditService(IAuditLogger auditLogger) {
    this.auditLogger = auditLogger;
  }

  public void activitiesRead() {
    auditLogger.sendAudit("Activities were read");
  }

  public void activityCreated(Activity activity) {
    auditLogger.sendAudit(
        String.format("Activity %s created with ID: %s", activity.name(), activity.id()));
  }

  public void activityRead(UUID id) {
    auditLogger.sendAudit( String.format("Activity %s read", id));
  }

  public void activityDeleted(UUID id) {
    auditLogger.sendAudit(String.format("Activity %s deleted", id));
  }

  public void activityDeletionFailed() {
    auditLogger.sendAudit("Failed to delete activity");
  }
}
