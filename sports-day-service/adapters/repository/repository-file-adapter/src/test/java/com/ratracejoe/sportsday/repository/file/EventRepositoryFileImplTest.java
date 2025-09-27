package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.*;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class EventRepositoryFileImplTest {
  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    EventRepositoryFileImpl repository = new EventRepositoryFileImpl(tempDir);
    Event domain =
        new Event(
            UUID.randomUUID(),
            UUID.randomUUID(),
            EventState.CREATING,
            CompetitorType.TEAM,
            ScoreType.POINTS_SCORE_SHEET,
            3);

    // When
    String csv = repository.toCsv(domain);
    Event retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void createAndGet(@TempDir Path tempDir) {
    // Given
    EventRepositoryFileImpl repository = new EventRepositoryFileImpl(tempDir);
    Event event =
        new Event(
            UUID.randomUUID(),
            UUID.randomUUID(),
            EventState.CREATING,
            CompetitorType.TEAM,
            ScoreType.POINTS_SCORE_SHEET,
            3);

    // When
    repository.save(event);
    Event found = repository.getById(event.id());

    // Then
    assertThat(found).isEqualTo(event);
  }
}
