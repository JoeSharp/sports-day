package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.repository.redis.service.CompetitorRepositoryRedisImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompetitorRepositoryRedisImplTest extends AbstractRedisTest {
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
