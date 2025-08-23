package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.model.entity.ActivityEntity;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuditService {
  public static final String AUDIT_TOPIC = "audit";

  private final KafkaTemplate<String, String> kafka;

  public void activitiesRead() {
    kafka.send(AUDIT_TOPIC, "Activities were read");
  }

  public void activityCreated(ActivityEntity entity) {
    kafka.send(
        AUDIT_TOPIC,
        String.format("Activity %s created with ID: %s", entity.getName(), entity.getId()));
  }

  public void activityRead(UUID id) {
    kafka.send(AUDIT_TOPIC, String.format("Activity %s read", id));
  }

  public void activityDeleted(UUID id) {
    kafka.send(AUDIT_TOPIC, String.format("Activity %s deleted", id));
  }

  public void activityDeletionFailed() {
    kafka.send(AUDIT_TOPIC, "Failed to delete activity");
  }
}
