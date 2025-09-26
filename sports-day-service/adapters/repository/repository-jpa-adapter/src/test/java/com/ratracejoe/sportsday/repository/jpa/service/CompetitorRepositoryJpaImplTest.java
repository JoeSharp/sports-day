package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CompetitorRepositoryJpaImplTest {
  @Autowired private CompetitorRepositoryJpaImpl competitorRepository;

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
