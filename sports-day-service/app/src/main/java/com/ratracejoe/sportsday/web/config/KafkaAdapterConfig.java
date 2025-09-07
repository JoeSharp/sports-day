package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.kafka.KafkaAuditLoggerImpl;
import com.ratracejoe.sportsday.ports.outgoing.IAuditLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaAdapterConfig {

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate(
      ProducerFactory<String, String> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }

  @Bean
  public IAuditLogger auditLogger(KafkaTemplate<String, String> template) {
    return new KafkaAuditLoggerImpl(template);
  }
}
