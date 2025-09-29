package com.ratracejoe.sportsday.web.util;

import lombok.Getter;
import org.springframework.test.context.DynamicPropertyRegistry;

public class SportsDayContainers {

  private static final KeycloakExtension keycloakExtension = new KeycloakExtension();
  @Getter private static final KafkaExtension kafkaExtension = new KafkaExtension();
  private static final RedisExtension redisExtension = new RedisExtension();

  /**
   * It's a bit odd, but I think that the Cucumber runner is overriding the lifecycle methods used
   * by JUnit 5 to initialise Extensions, and the Testcontainers annotation. So here we are
   * overriding those Cucumber lifecycle methods and manually setting up those things.
   */
  public static void beforeAll() {
    redisExtension.beforeAll();
    kafkaExtension.beforeAll();
    keycloakExtension.beforeAll();
  }

  public static void afterAll() {
    redisExtension.afterAll();
    kafkaExtension.afterAll();
    keycloakExtension.afterAll();
  }

  public static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    keycloakExtension.registerDynamicProperties(registry);
    kafkaExtension.registerDynamicProperties(registry);
    redisExtension.registerDynamicProperties(registry);
  }
}
