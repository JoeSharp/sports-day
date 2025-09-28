package com.ratracejoe.sportsday.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ratracejoe.sportsday.domain.model.*;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EventCommandServiceTest extends GenericCommandServiceTest {
  @Test
  void createTeam() throws InvalidCommandException {
    // When
    UUID activityId = UUID.randomUUID();
    commandService.handleCommand("event create " + activityId + " TEAM POINTS_SCORE_SHEET 11");

    // Then
    verify(eventService)
        .createEvent(activityId, CompetitorType.TEAM, ScoreType.POINTS_SCORE_SHEET, 11);
  }

  @Test
  void getById() throws InvalidCommandException {
    // When
    Event event =
        new Event(
            UUID.randomUUID(),
            UUID.randomUUID(),
            EventState.CREATING,
            CompetitorType.TEAM,
            ScoreType.POINTS_SCORE_SHEET,
            3);
    when(eventService.getById(event.id())).thenReturn(event);
    commandService.handleCommand("event getById " + event.id());

    // Then
    verify(eventService, times(1)).getById(event.id());
    verify(responseListener, times(1)).handleResponse(any(String.class));
  }

  @Test
  void getAll() throws InvalidCommandException {
    // When
    List<Event> domainList =
        List.of(
            new Event(
                UUID.randomUUID(),
                UUID.randomUUID(),
                EventState.CREATING,
                CompetitorType.TEAM,
                ScoreType.POINTS_SCORE_SHEET,
                3),
            new Event(
                UUID.randomUUID(),
                UUID.randomUUID(),
                EventState.FINISHED,
                CompetitorType.INDIVIDUAL,
                ScoreType.FINISHING_ORDER,
                4),
            new Event(
                UUID.randomUUID(),
                UUID.randomUUID(),
                EventState.FINISHED,
                CompetitorType.TEAM,
                ScoreType.TIMED_FINISHING_ORDER,
                10));
    when(eventService.getAll()).thenReturn(domainList);
    commandService.handleCommand("event getAll");

    // Then
    verify(eventService, times(1)).getAll();
    verify(responseListener, times(3)).handleResponse(any(String.class));
  }

  @Test
  void deleteById() throws InvalidCommandException {
    // Given
    UUID id = UUID.randomUUID();

    // When
    commandService.handleCommand("event deleteById " + id);

    // Then
    verify(eventService).deleteById(id);
  }

  @Test
  void getAllInvalid() {
    // When
    assertThatThrownBy(() -> commandService.handleCommand("event getAll foobar"))
        .isInstanceOf(InvalidCommandException.class);
  }
}
