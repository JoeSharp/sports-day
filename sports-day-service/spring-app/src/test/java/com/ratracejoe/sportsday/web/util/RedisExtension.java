package com.ratracejoe.sportsday.web.util;

import com.redis.testcontainers.RedisContainer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.utility.DockerImageName;

public class RedisExtension {
    private static final int REDIS_PORT = 6379;

    private static final RedisContainer redis =
            new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

    public void beforeAll() {
        redis.start();
    }

    public void afterAll() {
        redis.stop();
    }
    public void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
    }
}
