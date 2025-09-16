package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import java.util.List;
import java.util.UUID;
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
    Competitor max = fixtures.findCompetitor(competitors, "Verstappen");
    Competitor lewis = fixtures.findCompetitor(competitors, "Hamilton");
    Competitor lando = fixtures.findCompetitor(competitors, "Norris");
    Competitor charles = fixtures.findCompetitor(competitors, "Leclerc");

    // When
    scoreService.finishingOrderService().passFinishLine(event.id(), lewis.id());
    scoreService.finishingOrderService().passFinishLine(event.id(), max.id());
    scoreService.finishingOrderService().passFinishLine(event.id(), charles.id());
    scoreService.finishingOrderService().passFinishLine(event.id(), lando.id());
    FinishingOrder finishingOrder =
        scoreService.finishingOrderService().getFinishingOrder(event.id());

    // Then
    assertThat(finishingOrder).isNotNull();
    assertThat(finishingOrder.finishers())
        .containsExactly(lewis.id(), max.id(), charles.id(), lando.id());
  }

  @Test
  void passFinishLineTimed() {
    // Given
    Event event = fixtures.finishOrderEventStarted(true);
    List<Competitor> competitors = eventService.getParticipants(event.id());
    Competitor max = fixtures.findCompetitor(competitors, "Verstappen");
    Competitor lewis = fixtures.findCompetitor(competitors, "Hamilton");
    Competitor lando = fixtures.findCompetitor(competitors, "Norris");
    Competitor charles = fixtures.findCompetitor(competitors, "Leclerc");

    // When
    scoreService.timedFinishingOrderService().passFinishLineInTime(event.id(), max.id(), 2000);
    scoreService.timedFinishingOrderService().passFinishLineInTime(event.id(), lewis.id(), 1600);
    scoreService.timedFinishingOrderService().passFinishLineInTime(event.id(), lando.id(), 3000);
    scoreService.timedFinishingOrderService().passFinishLineInTime(event.id(), charles.id(), 1500);

    TimedFinishingOrder timedFinishingOrder =
        scoreService.timedFinishingOrderService().getTimedFinishingOrder(event.id());
    List<UUID> finishingOrder = timedFinishingOrder.finishingOrder();

    // Then
    assertThat(timedFinishingOrder).isNotNull();
    assertThat(timedFinishingOrder.finishTimeMilliseconds())
        .containsEntry(max.id(), 2000L)
        .containsEntry(lewis.id(), 1600L)
        .containsEntry(lando.id(), 3000L)
        .containsEntry(charles.id(), 1500L);
    assertThat(finishingOrder).containsExactly(charles.id(), lewis.id(), max.id(), lando.id());
  }

  @Test
  void scoreSheet() {
    // Given
    Event event = fixtures.scoredEventStarted();
    List<Competitor> competitors = eventService.getParticipants(event.id());
    Competitor manchesterUnited = fixtures.findCompetitor(competitors, "Manchester United");
    Competitor liverpool = fixtures.findCompetitor(competitors, "Liverpool");

    // When
    scoreService.pointScoreService().addPoints(event.id(), manchesterUnited.id(), 1);
    scoreService.pointScoreService().addPoints(event.id(), liverpool.id(), 1);
    scoreService.pointScoreService().addPoints(event.id(), liverpool.id(), 1);
    scoreService.pointScoreService().addPoints(event.id(), manchesterUnited.id(), 1);
    scoreService.pointScoreService().addPoints(event.id(), manchesterUnited.id(), 1);
    scoreService.pointScoreService().addPoints(event.id(), liverpool.id(), 1);
    scoreService.pointScoreService().addPoints(event.id(), liverpool.id(), 1);
    PointScoreSheet result = scoreService.pointScoreService().getPoints(event.id());

    // Then
    assertThat(result).isNotNull();
    assertThat(result.scores())
        .containsEntry(manchesterUnited.id(), 3)
        .containsEntry(liverpool.id(), 4);
  }
}
