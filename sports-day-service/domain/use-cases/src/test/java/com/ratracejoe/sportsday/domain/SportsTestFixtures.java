package com.ratracejoe.sportsday.domain;

import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.domain.model.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class SportsTestFixtures {
  private final MemoryAdapters memoryAdapters;

  public SportsTestFixtures() {
    this.memoryAdapters = new MemoryAdapters();
  }

  public MemoryAdapters memoryAdapters() {
    return memoryAdapters;
  }

  public void userLoggedInWithRole(SportsDayRole... roles) {
    memoryAdapters.setCurrentUser(
        new SportsDayUser(UUID.randomUUID().toString(), "Test User", List.of(roles)));
  }

  public Activity activityCreated() {
    userLoggedInWithRole(SportsDayRole.PE_TEACHER);
    return memoryAdapters
        .activityService()
        .createActivity("Walking" + UUID.randomUUID(), "Burns calories" + UUID.randomUUID());
  }

  public Event eventPrepared() {
    Activity activity = activityCreated();
    return memoryAdapters
        .eventService()
        .createEvent(activity.id(), CompetitorType.INDIVIDUAL, ScoreType.FINISHING_ORDER, 4);
  }

  public Event eventCreatedWithSoloParticipant() {
    Event event = eventPrepared();
    Competitor competitor =
        memoryAdapters.competitorService().createCompetitor(CompetitorType.INDIVIDUAL, "Han Solo");
    memoryAdapters.eventService().registerParticipant(event.id(), competitor.id());
    return event;
  }

  public Event scoredIndividualEventStarted() {
    Activity activity = activityCreated();
    Event event =
        memoryAdapters
            .eventService()
            .createEvent(activity.id(), CompetitorType.INDIVIDUAL, ScoreType.POINTS_SCORE_SHEET, 2);

    Stream.of("Tyson", "Holyfield")
        .map(memoryAdapters.competitorService()::createCompetitor)
        .map(Competitor::id)
        .forEach(cId -> memoryAdapters.eventService().registerParticipant(event.id(), cId));
    memoryAdapters.eventService().startEvent(event.id());
    return event;
  }

  public Event scoredTeamEventStarted() {
    Activity activity = activityCreated();
    Event event =
        memoryAdapters
            .eventService()
            .createEvent(activity.id(), CompetitorType.INDIVIDUAL, ScoreType.POINTS_SCORE_SHEET, 2);

    Stream.of("Manchester United", "Liverpool")
        .map(memoryAdapters.competitorService()::createCompetitor)
        .map(Competitor::id)
        .forEach(cId -> memoryAdapters.eventService().registerParticipant(event.id(), cId));
    memoryAdapters.eventService().startEvent(event.id());
    return event;
  }

  public Event finishOrderEventStarted() {
    return finishOrderEventStarted(false);
  }

  public Competitor findCompetitor(List<Competitor> competitors, String name) {
    return competitors.stream().filter(c -> c.name().contains(name)).findFirst().orElseThrow();
  }

  public Event finishOrderEventStarted(boolean timed) {
    ScoreType scoreType = timed ? ScoreType.TIMED_FINISHING_ORDER : ScoreType.FINISHING_ORDER;
    Activity activity = activityCreated();
    Event event =
        memoryAdapters
            .eventService()
            .createEvent(activity.id(), CompetitorType.INDIVIDUAL, scoreType, 10);

    Stream.of("Max Verstappen", "Lewis Hamilton", "Lando Norris", "Charles Leclerc")
        .map(memoryAdapters.competitorService()::createCompetitor)
        .map(Competitor::id)
        .forEach(cId -> memoryAdapters.eventService().registerParticipant(event.id(), cId));
    memoryAdapters.eventService().startEvent(event.id());
    return event;
  }
}
