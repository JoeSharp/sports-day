package com.ratracejoe.sportsday.repository.jpa.service;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.utility.DockerImageName;

public class AbstractRedisTest {
  private static final int REDIS_PORT = 6379;

  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  @BeforeAll
  public static void beforeAll() {
    redis.start();
  }

  @AfterAll
  public static void afterAll() {
    redis.stop();
  }

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }
}
