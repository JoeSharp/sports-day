package com.ratracejoe.sportsday.web.util;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.kafka.ConfluentKafkaContainer;

public class KafkaExtension {
  private static final ConfluentKafkaContainer kafka =
      new ConfluentKafkaContainer("confluentinc/cp-kafka:7.4.4");

  private final List<String> auditsReceived = new ArrayList<>();

  public void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
  }

  public void processMessage(String content) {
    auditsReceived.add(content);
  }

  public void beforeAll() {
    kafka.start();
  }

  public void afterAll() {
    kafka.stop();
  }

  public List<String> getAuditsReceived() {
    return new ArrayList<>(auditsReceived);
  }
}
