package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.repository.redis.service.TeamRepositoryRedisImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ExtendWith(RedisTestContainerExtension.class)
class TeamRepositoryRedisImplTest {

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    RedisTestContainerExtension.registerDynamicProperties(registry);
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
