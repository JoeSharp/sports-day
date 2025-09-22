package com.ratracejoe.sportsday.repository.jpa.service;

import static com.ratracejoe.sportsday.repository.jpa.RedisTestApplication.REDIS_PORT;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.repository.redis.service.TeamRepositoryRedisImpl;
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
class TeamRepositoryRedisImplTest {

  @Container
  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }

  @Autowired private TeamRepositoryRedisImpl teamRepository;

  @Test
  void createAndGet() {
    // Given
    Team team = new Team(UUID.randomUUID(), "The Rats");

    // When
    teamRepository.save(team);
    Team found = teamRepository.getById(team.id());

    // Then
    assertThat(found).isEqualTo(team);
  }
}
