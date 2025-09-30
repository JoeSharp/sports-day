package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScoreServiceTest {
  private SportsTestFixtures fixtures;
  private IEventService eventService;
  private ITimedFinishingOrderService timedFinishingOrderService;
  private IFinishingOrderService finishingOrderService;
  private IPointScoreService pointScoreService;
  private IScoreService scoreService;

  @BeforeEach
  void beforeEach() {
    fixtures = new SportsTestFixtures();
    scoreService = fixtures.memoryAdapters().scoreService();
    eventService = fixtures.memoryAdapters().eventService();
    finishingOrderService = fixtures.memoryAdapters().finishingOrderService();
    timedFinishingOrderService = fixtures.memoryAdapters().timedFinishingOrderService();
    pointScoreService = fixtures.memoryAdapters().pointScoreService();
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
    finishingOrderService.passFinishLine(event.id(), lewis.id());
    finishingOrderService.passFinishLine(event.id(), max.id());
    finishingOrderService.passFinishLine(event.id(), charles.id());
    finishingOrderService.passFinishLine(event.id(), lando.id());
    FinishingOrder finishingOrder = finishingOrderService.getFinishingOrder(event.id());

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
    timedFinishingOrderService.passFinishLineInTime(event.id(), max.id(), 2000);
    timedFinishingOrderService.passFinishLineInTime(event.id(), lewis.id(), 1600);
    timedFinishingOrderService.passFinishLineInTime(event.id(), lando.id(), 3000);
    timedFinishingOrderService.passFinishLineInTime(event.id(), charles.id(), 1500);

    TimedFinishingOrder timedFinishingOrder =
        timedFinishingOrderService.getTimedFinishingOrder(event.id());
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
  void individualScoredEvent() {
    // Given
    Event event = fixtures.scoredIndividualEventStarted();
    List<Competitor> competitors = eventService.getParticipants(event.id());
    Competitor tyson = fixtures.findCompetitor(competitors, "Tyson");
    Competitor holyfield = fixtures.findCompetitor(competitors, "Holyfield");

    // When
    pointScoreService.addPoints(event.id(), tyson.id(), 1);
    pointScoreService.addPoints(event.id(), holyfield.id(), 1);
    pointScoreService.addPoints(event.id(), holyfield.id(), 1);
    pointScoreService.addPoints(event.id(), tyson.id(), 1);
    pointScoreService.addPoints(event.id(), tyson.id(), 1);
    pointScoreService.addPoints(event.id(), holyfield.id(), 1);
    pointScoreService.addPoints(event.id(), holyfield.id(), 1);
    PointScoreSheet result = pointScoreService.getPoints(event.id());

    // Then
    assertThat(result).isNotNull();
    assertThat(result.scores()).containsEntry(tyson.id(), 3).containsEntry(holyfield.id(), 4);
  }

  @Test
  void teamScoredEvent() {
    // Given
    Event event = fixtures.scoredTeamEventStarted();
    List<Competitor> competitors = eventService.getParticipants(event.id());
    Competitor manchesterUnited = fixtures.findCompetitor(competitors, "Manchester United");
    Competitor liverpool = fixtures.findCompetitor(competitors, "Liverpool");

    // When
    pointScoreService.addPoints(event.id(), manchesterUnited.id(), 1);
    pointScoreService.addPoints(event.id(), liverpool.id(), 1);
    pointScoreService.addPoints(event.id(), manchesterUnited.id(), 1);
    pointScoreService.addPoints(event.id(), liverpool.id(), 1);
    pointScoreService.addPoints(event.id(), manchesterUnited.id(), 1);
    PointScoreSheet result = pointScoreService.getPoints(event.id());

    // Then
    assertThat(result).isNotNull();
    assertThat(result.scores())
        .containsEntry(manchesterUnited.id(), 3)
        .containsEntry(liverpool.id(), 2);
  }
}
