package com.ratracejoe.sportsday.service;

import com.ratracejoe.sportsday.repository.entity.ActivityEntity;
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
}
