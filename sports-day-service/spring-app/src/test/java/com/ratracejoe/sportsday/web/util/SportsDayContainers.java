package com.ratracejoe.sportsday.web.util;

import com.redis.testcontainers.RedisContainer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

public class SportsDayContainers {

  private static final KeycloakExtension keycloakExtension = new KeycloakExtension();

  private static final ConfluentKafkaContainer kafka =
      new ConfluentKafkaContainer("confluentinc/cp-kafka:7.4.4");

  private static final int REDIS_PORT = 6379;

  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  @Getter private static final List<String> auditsReceived = new ArrayList<>();

  public static void processMessage(String content) {
    auditsReceived.add(content);
  }

  /**
   * It's a bit odd, but I think that the Cucumber runner is overriding the lifecycle methods used
   * by JUnit 5 to initialise Extensions, and the Testcontainers annotation. So here we are
   * overriding those Cucumber lifecycle methods and manually setting up those things.
   */
  public static void beforeAll() {
    kafka.start();
    redis.start();
    keycloakExtension.beforeAll();
  }

  public static void afterAll() {
    kafka.stop();
    redis.stop();
    keycloakExtension.afterAll();
  }

  public static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add(
        "spring.security.oauth2.resourceserver.jwt.issuer-uri", keycloakExtension::getIssuerUri);
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }
}
