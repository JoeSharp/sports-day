package com.ratracejoe.sportsday.config;

import static com.ratracejoe.sportsday.service.AuditService.AUDIT_TOPIC;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

public class AuditConfig {
  @Bean
  public NewTopic auditTopic() {
    return TopicBuilder.name(AUDIT_TOPIC).partitions(10).replicas(1).build();
  }
}
