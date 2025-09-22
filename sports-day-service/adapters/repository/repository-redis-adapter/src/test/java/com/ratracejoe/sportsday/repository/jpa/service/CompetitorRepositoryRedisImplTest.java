package com.ratracejoe.sportsday.repository.jpa.service;

import static com.ratracejoe.sportsday.repository.jpa.RedisTestApplication.REDIS_PORT;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.repository.redis.service.CompetitorRepositoryRedisImpl;
import com.redis.testcontainers.RedisContainer;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class CompetitorRepositoryRedisImplTest {

  @Container
  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }

  @Autowired private CompetitorRepositoryRedisImpl competitorRepository;

  @Test
  void createAndGet() {
    // Given
    Competitor competitor =
        new Competitor(UUID.randomUUID(), CompetitorType.INDIVIDUAL, "Mr Blobby");

    // When
    competitorRepository.save(competitor);
    Competitor found = competitorRepository.getById(competitor.id());

    // Then
    assertThat(found).isEqualTo(competitor);
  }
}
