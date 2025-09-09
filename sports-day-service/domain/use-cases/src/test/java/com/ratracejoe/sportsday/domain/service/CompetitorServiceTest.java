package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.outgoing.MemoryCompetitorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompetitorServiceTest {
  private CompetitorService competitorFacade;

  @BeforeEach
  void beforeEach() {
    MemoryCompetitorRepository competitorRepository = new MemoryCompetitorRepository();
    competitorFacade = new CompetitorService(competitorRepository);
  }

  @Test
  void createCompetitor() {
    // Given
    String competitorName = "Max Power";

    // When
    Competitor competitor = competitorFacade.createCompetitor(competitorName);

    // Then
    assertThat(competitor).extracting(Competitor::id).isNotNull();
    assertThat(competitor).extracting(Competitor::name).isEqualTo(competitorName);
  }

  @Test
  void getById() throws NotFoundException {
    // Given
    Competitor competitor = competitorFacade.createCompetitor("Min Juice");

    // When
    Competitor found = competitorFacade.getById(competitor.id());

    // Then
    assertThat(found).isEqualTo(competitor);
  }
}
