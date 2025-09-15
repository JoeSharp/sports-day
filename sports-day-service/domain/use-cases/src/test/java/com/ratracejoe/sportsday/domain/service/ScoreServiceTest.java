package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
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
    FinishingOrder finishingOrder = scoreService.getFinishingOrder(event.id());

    // Then
    assertThat(finishingOrder).isNotNull();
    assertThat(finishingOrder.finishers())
        .containsExactlyElementsOf(competitors.stream().map(Competitor::id).toList());
  }

  @Test
  void passFinishLineTimed() {
    // Given
    Event event = fixtures.finishOrderEventStarted(true);
    List<Competitor> competitors = eventService.getParticipants(event.id());

    // When
    AtomicLong finishTime = new AtomicLong();
    competitors.stream()
        .map(Competitor::id)
        .forEach(
            cId ->
                scoreService.passFinishLineInTime(event.id(), cId, finishTime.getAndDecrement()));
    TimedFinishingOrder finishingOrder = scoreService.getTimedFinishingOrder(event.id());

    // Then
    assertThat(finishingOrder).isNotNull();
    assertThat(finishingOrder.finishTimeMilliseconds())
        .containsOnlyKeys(competitors.stream().map(Competitor::id).toList());
  }

  @Test
  void scoreSheet() {
    // Given
    Event event = fixtures.scoredEventStarted();
  }
}
