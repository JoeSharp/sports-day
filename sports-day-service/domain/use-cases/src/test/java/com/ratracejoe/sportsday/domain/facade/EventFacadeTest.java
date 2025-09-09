package com.ratracejoe.sportsday.domain.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.exception.InvalidEventStateException;
import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.fixtures.FixtureFactory;
import com.ratracejoe.sportsday.domain.model.*;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventFacadeTest {
  private ActivityFacade activityFacade;
  private EventFacade eventFacade;
  private CompetitorFacade competitorFacade;

  @BeforeEach
  void beforeEach() {
    FixtureFactory fixtureFactory = new FixtureFactory();
    activityFacade = fixtureFactory.activityFacade();
    eventFacade = fixtureFactory.eventFacade();
    competitorFacade = fixtureFactory.competitorFacade();
  }

  @Test
  void createEvent() {
    // Given
    Activity walking = activityFacade.createActivity("Walking", "Burns calories");

    // When
    Event smallRamble = eventFacade.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);

    // Then
    assertThat(smallRamble).isNotNull().extracting(Event::id).isNotNull();
    assertThat(smallRamble).extracting(Event::state).isEqualTo(EventState.CREATING);
  }

  @Test
  void hostEvent() {
    // Given
    Activity walking = activityFacade.createActivity("Walking", "Burns calories");
    List<Competitor> competitors =
        Stream.of("Alan", "Bobbie", "Cherly", "Diane")
            .map(competitorFacade::createCompetitor)
            .toList();

    // When
    Event smallRamble = eventFacade.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);

    competitors.stream()
        .map(Competitor::id)
        .forEach(cId -> eventFacade.registerParticipant(smallRamble.id(), cId));
    eventFacade.startEvent(smallRamble.id());
    Event smallRambleUnderway = eventFacade.getById(smallRamble.id());

    // Then
    assertThat(smallRambleUnderway).isNotNull();
  }

  @Test
  void startEvent() {
    // Given
    Activity walking = activityFacade.createActivity("Walking", "Burns calories");
    Event smallRamble = eventFacade.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);
    Competitor solo = competitorFacade.createCompetitor("Han Solo");
    eventFacade.registerParticipant(smallRamble.id(), solo.id());

    // When
    eventFacade.startEvent(smallRamble.id());
    Event after = eventFacade.getById(smallRamble.id());

    // Then
    assertThat(after).extracting(Event::state).isEqualTo(EventState.STARTED);
  }

  @Test
  void cannotStartEventWithoutParticipants() {
    // Given
    Activity walking = activityFacade.createActivity("Walking", "Burns calories");
    Event smallRamble = eventFacade.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);

    // When, Then
    assertThatThrownBy(() -> eventFacade.startEvent(smallRamble.id()))
        .isInstanceOf(NoParticipantsException.class);
  }

  @Test
  void cannotStartEventTwice() {
    // Given
    Activity walking = activityFacade.createActivity("Walking", "Burns calories");
    Event smallRamble = eventFacade.createEvent(walking.id(), ParticipantType.INDIVIDUAL, 4);
    Competitor solo = competitorFacade.createCompetitor("Han Solo");
    eventFacade.registerParticipant(smallRamble.id(), solo.id());
    eventFacade.startEvent(smallRamble.id());

    // When, Then
    assertThatThrownBy(() -> eventFacade.startEvent(smallRamble.id()))
        .isInstanceOf(InvalidEventStateException.class);
  }

  @Test
  void cannotRegisterTooManyParticipants() {}

  @Test
  void cannotEndEventTwice() {}

  @Test
  void cannotEndEventWithoutResults() {}
}
