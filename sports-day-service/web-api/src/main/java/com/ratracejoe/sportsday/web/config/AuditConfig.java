package com.ratracejoe.sportsday.web.config;

import static com.ratracejoe.sportsday.web.service.KafkaAuditLoggerImpl.AUDIT_TOPIC;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class AuditConfig {
  @Bean
  public NewTopic auditTopic() {
    return TopicBuilder.name(AUDIT_TOPIC).partitions(10).replicas(1).build();
  }
}
