package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetitorServiceTest {
  private CompetitorService competitorService;

  @BeforeEach
  void beforeEach() {
    SportsTestFixtures fixtures = new SportsTestFixtures();
    competitorService = fixtures.memoryAdapters().competitorService();
  }

  @Test
  void createCompetitor() {
    // Given
    String competitorName = "Max Power";

    // When
    Competitor competitor = competitorService.createCompetitor(competitorName);

    // Then
    assertThat(competitor).extracting(Competitor::id).isNotNull();
    assertThat(competitor).extracting(Competitor::name).isEqualTo(competitorName);
  }

  @Test
  void getById() throws NotFoundException {
    // Given
    Competitor competitor = competitorService.createCompetitor("Min Juice");

    // When
    Competitor found = competitorService.getById(competitor.id());

    // Then
    assertThat(found).isEqualTo(competitor);
  }
}
