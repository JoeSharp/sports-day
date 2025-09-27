package com.ratracejoe.sportsday.repository.file.score;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TimedFinishingOrderRepositoryFileIImplTest {
  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    TimedFinishingOrderRepositoryFileImpl repository =
        new TimedFinishingOrderRepositoryFileImpl(tempDir);
    TimedFinishingOrder domain =
        new TimedFinishingOrder(
            UUID.randomUUID(),
            Map.of(UUID.randomUUID(), 5L, UUID.randomUUID(), 7L, UUID.randomUUID(), 3L));

    // When
    String csv = repository.toCsv(domain);
    TimedFinishingOrder retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void createAndFetch(@TempDir Path tempDir) {
    // Given
    TimedFinishingOrderRepositoryFileImpl repository =
        new TimedFinishingOrderRepositoryFileImpl(tempDir);
    TimedFinishingOrder finishingOrder =
        new TimedFinishingOrder(
            UUID.randomUUID(),
            Map.of(UUID.randomUUID(), 5L, UUID.randomUUID(), 7L, UUID.randomUUID(), 3L));

    // When
    repository.save(finishingOrder);
    TimedFinishingOrder found = repository.getById(finishingOrder.eventId());

    // Then
    assertThat(found).isEqualTo(finishingOrder);
  }
}
