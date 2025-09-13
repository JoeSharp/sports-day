package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.exception.InvalidEventStateException;
import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.model.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventServiceTest {
  private SportsTestFixtures fixtures;
  private ActivityService activityService;
  private EventService eventService;
  private CompetitorService competitorService;

  @BeforeEach
  void beforeEach() {
    MemoryAdapters adapters = new MemoryAdapters();
    fixtures = new SportsTestFixtures(adapters);
    activityService = adapters.activityService();
    eventService = adapters.eventService();
    competitorService = adapters.competitorService();
  }

  @Test
  void createEvent() {
    // Given
    Activity activity = fixtures.activityCreated();

    // When
    Event event =
        eventService.createEvent(
            activity.id(), ParticipantType.INDIVIDUAL, GoalType.NUMBER_POINTS, 4);

    // Then
    assertThat(event).isNotNull().extracting(Event::id).isNotNull();
    assertThat(event).extracting(Event::state).isEqualTo(EventState.CREATING);
  }

  @Test
  void hostEvent() {
    // Given
    Activity activity = fixtures.activityCreated();
    List<Competitor> competitors =
        Stream.of("Alan", "Bobbie", "Cherly", "Diane")
            .map(competitorService::createCompetitor)
            .toList();

    // When
    Event event =
        eventService.createEvent(
            activity.id(), ParticipantType.INDIVIDUAL, GoalType.ORDER_OVER_FIXED_DISTANCE, 4);

    competitors.stream()
        .map(Competitor::id)
        .forEach(cId -> eventService.registerParticipant(event.id(), cId));
    eventService.startEvent(event.id());
    Event eventUnderway = eventService.getById(event.id());
    List<Competitor> competitorsFound = eventService.getParticipants(event.id());

    // Then
    assertThat(eventUnderway).isNotNull();
    assertThat(competitorsFound).containsExactlyInAnyOrderElementsOf(competitors);
  }

  @Test
  void startEvent() {
    // Given
    Event event = fixtures.eventCreatedWithSoloParticipant();

    // When
    eventService.startEvent(event.id());
    Event after = eventService.getById(event.id());

    // Then
    assertThat(after).extracting(Event::state).isEqualTo(EventState.STARTED);
  }

  @Test
  void cannotStartEventWithoutParticipants() {
    // Given
    Event event = fixtures.eventPrepared();

    // When, Then
    assertThatThrownBy(() -> eventService.startEvent(event.id()))
        .isInstanceOf(NoParticipantsException.class);
  }

  @Test
  void cannotStartEventTwice() {
    // Given
    Event event = fixtures.eventCreatedWithSoloParticipant();

    // When
    eventService.startEvent(event.id());

    // Then
    assertThatThrownBy(() -> eventService.startEvent(event.id()))
        .isInstanceOf(InvalidEventStateException.class);
  }

  @Test
  void cannotRegisterTooManyParticipants() {}

  @Test
  void cannotEndEventTwice() {}

  @Test
  void cannotEndEventWithoutResults() {}
}
