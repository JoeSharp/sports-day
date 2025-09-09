package com.ratracejoe.sportsday.domain.facade;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.fixtures.FixtureFactory;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
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
}
