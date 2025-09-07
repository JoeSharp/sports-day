package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.kafka.KafkaAuditLoggerImpl;
import com.ratracejoe.sportsday.ports.outgoing.IAuditLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class AuditAdapterConfig {

  @Bean
  public IAuditLogger auditLogger(KafkaTemplate<String, String> template) {
    return new KafkaAuditLoggerImpl(template);
  }
}
