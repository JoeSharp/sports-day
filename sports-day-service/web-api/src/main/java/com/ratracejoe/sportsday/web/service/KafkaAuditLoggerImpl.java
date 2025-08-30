package com.ratracejoe.sportsday.web.service;

import com.ratracejoe.sportsday.outgoing.IAuditLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaAuditLoggerImpl implements IAuditLogger {
  public static final String AUDIT_TOPIC = "audit";

  private final KafkaTemplate<String, String> kafka;

  @Override
  public void sendAudit(String msg) {
    kafka.send(AUDIT_TOPIC, msg);
  }
}
