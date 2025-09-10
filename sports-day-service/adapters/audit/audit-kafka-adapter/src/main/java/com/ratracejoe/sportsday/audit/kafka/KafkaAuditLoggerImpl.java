package com.ratracejoe.sportsday.audit.kafka;

import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaAuditLoggerImpl implements IAuditLogger {
  public static final String AUDIT_TOPIC = "audit";

  private final KafkaTemplate<String, String> kafka;

  public KafkaAuditLoggerImpl(KafkaTemplate<String, String> kafka) {
    this.kafka = kafka;
  }

  @Override
  public void sendAudit(String msg) {
    kafka.send(AUDIT_TOPIC, msg);
  }
}
