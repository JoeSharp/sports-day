package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.repository.redis.service.CompetitorRepositoryRedisImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ExtendWith(RedisTestContainerExtension.class)
class CompetitorRepositoryRedisImplTest {

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    RedisTestContainerExtension.registerDynamicProperties(registry);
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
