package com.ratracejoe.sportsday.domain;

import com.ratracejoe.sportsday.domain.model.*;
import java.util.UUID;
import java.util.stream.Stream;

public class SportsTestFixtures {
  private final MemoryAdapters memoryAdapters;

  public SportsTestFixtures(final MemoryAdapters memoryAdapters) {
    this.memoryAdapters = memoryAdapters;
  }

  public Activity activityCreated() {
    return memoryAdapters
        .activityService()
        .createActivity("Walking" + UUID.randomUUID(), "Burns calories" + UUID.randomUUID());
  }

  public Event eventPrepared() {
    Activity activity = activityCreated();
    return memoryAdapters
        .eventService()
        .createEvent(
            activity.id(), ParticipantType.INDIVIDUAL, GoalType.ORDER_OVER_FIXED_DISTANCE, 4);
  }

  public Event eventCreatedWithSoloParticipant() {
    Event event = eventPrepared();
    Competitor competitor = memoryAdapters.competitorService().createCompetitor("Han Solo");
    memoryAdapters.eventService().registerParticipant(event.id(), competitor.id());
    return event;
  }

  public Event finishOrderEventStarted() {
    Activity activity = activityCreated();
    Event event =
        memoryAdapters
            .eventService()
            .createEvent(
                activity.id(), ParticipantType.INDIVIDUAL, GoalType.ORDER_OVER_FIXED_DISTANCE, 10);

    Stream.of("One", "Two", "Three", "Four")
        .map(memoryAdapters.competitorService()::createCompetitor)
        .map(Competitor::id)
        .forEach(cId -> memoryAdapters.eventService().registerParticipant(event.id(), cId));
    memoryAdapters.eventService().startEvent(event.id());
    return event;
  }
}
