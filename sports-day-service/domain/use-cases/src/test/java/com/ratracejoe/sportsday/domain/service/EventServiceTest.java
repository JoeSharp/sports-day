package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.exception.InvalidEventStateException;
import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.model.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventServiceTest {
  private ActivityService activityService;
  private EventService eventService;
  private CompetitorService competitorService;

  @BeforeEach
  void beforeEach() {
    MemoryAdapters adapters = new MemoryAdapters();
    activityService = adapters.activityService();
    eventService = adapters.eventService();
    competitorService = adapters.competitorService();
  }

  @Test
  void createEvent() {
    // Given
    Activity walking = activityService.createActivity("Walking", "Burns calories");

    // When
    Event smallRamble = eventService.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);

    // Then
    assertThat(smallRamble).isNotNull().extracting(Event::id).isNotNull();
    assertThat(smallRamble).extracting(Event::state).isEqualTo(EventState.CREATING);
  }

  @Test
  void hostEvent() {
    // Given
    Activity walking = activityService.createActivity("Walking", "Burns calories");
    List<Competitor> competitors =
        Stream.of("Alan", "Bobbie", "Cherly", "Diane")
            .map(competitorService::createCompetitor)
            .toList();

    // When
    Event smallRamble = eventService.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);

    competitors.stream()
        .map(Competitor::id)
        .forEach(cId -> eventService.registerParticipant(smallRamble.id(), cId));
    eventService.startEvent(smallRamble.id());
    Event smallRambleUnderway = eventService.getById(smallRamble.id());

    // Then
    assertThat(smallRambleUnderway).isNotNull();
  }

  @Test
  void startEvent() {
    // Given
    Activity walking = activityService.createActivity("Walking", "Burns calories");
    Event smallRamble = eventService.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);
    Competitor solo = competitorService.createCompetitor("Han Solo");
    eventService.registerParticipant(smallRamble.id(), solo.id());

    // When
    eventService.startEvent(smallRamble.id());
    Event after = eventService.getById(smallRamble.id());

    // Then
    assertThat(after).extracting(Event::state).isEqualTo(EventState.STARTED);
  }

  @Test
  void cannotStartEventWithoutParticipants() {
    // Given
    Activity walking = activityService.createActivity("Walking", "Burns calories");
    Event smallRamble = eventService.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);

    // When, Then
    assertThatThrownBy(() -> eventService.startEvent(smallRamble.id()))
        .isInstanceOf(NoParticipantsException.class);
  }

  @Test
  void cannotStartEventTwice() {
    // Given
    Activity walking = activityService.createActivity("Walking", "Burns calories");
    Event smallRamble = eventService.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);
    Competitor solo = competitorService.createCompetitor("Han Solo");
    eventService.registerParticipant(smallRamble.id(), solo.id());
    eventService.startEvent(smallRamble.id());

    // When, Then
    assertThatThrownBy(() -> eventService.startEvent(smallRamble.id()))
        .isInstanceOf(InvalidEventStateException.class);
  }

  @Test
  void cannotRegisterTooManyParticipants() {}

  @Test
  void cannotEndEventTwice() {}

  @Test
  void cannotEndEventWithoutResults() {}
}
