package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreServiceTest {
  private EventService eventService;
  private ScoreService scoreService;
  private SportsTestFixtures fixtures;

  @BeforeEach
  void beforeEach() {
    MemoryAdapters adapters = new MemoryAdapters();
    fixtures = new SportsTestFixtures(adapters);
    scoreService = adapters.scoreService();
    eventService = adapters.eventService();
  }

  @Test
  void passFinishLine() {
    // Given
    Event event = fixtures.finishOrderEventStarted();
    List<Competitor> competitors = eventService.getParticipants(event.id());

    // When
    competitors.stream()
            .map(Competitor::id)
            .forEach(cId -> scoreService.passFinishLine(event.id(), cId));

    // Then
  }
}
