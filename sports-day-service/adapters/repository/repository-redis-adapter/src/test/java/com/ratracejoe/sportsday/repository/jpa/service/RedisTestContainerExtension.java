package com.ratracejoe.sportsday.repository.jpa.service;

import static com.ratracejoe.sportsday.repository.jpa.RedisTestApplication.REDIS_PORT;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.utility.DockerImageName;

public class RedisTestContainerExtension implements BeforeAllCallback, AfterAllCallback {
  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    if (!redis.isRunning()) {
      redis.start();
    }
  }

  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    if (redis.isRunning()) {
      redis.stop();
    }
  }

  public static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }
}
