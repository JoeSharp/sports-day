package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.EventState;
import com.ratracejoe.sportsday.domain.model.ScoreType;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class EventRepositoryJpaImplTest {
  @Autowired private EventRepositoryJpaImpl eventRepository;

  @Test
  void createAndGet() {
    // Given
    Event event =
        new Event(
            UUID.randomUUID(),
            UUID.randomUUID(),
            EventState.CREATING,
            CompetitorType.TEAM,
            ScoreType.POINTS_SCORE_SHEET,
            3);

    // When
    eventRepository.save(event);
    Event found = eventRepository.getById(event.id());

    // Then
    assertThat(found).isEqualTo(event);
  }
}
