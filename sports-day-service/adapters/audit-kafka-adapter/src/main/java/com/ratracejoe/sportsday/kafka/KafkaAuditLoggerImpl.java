package com.ratracejoe.sportsday.kafka;

import com.ratracejoe.sportsday.ports.outgoing.IAuditLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaAuditLoggerImpl implements IAuditLogger {
  public static final String AUDIT_TOPIC = "audit";

  private final KafkaTemplate<String, String> kafka;

  @Override
  public void sendAudit(String msg) {
    kafka.send(AUDIT_TOPIC, msg);
  }
}
