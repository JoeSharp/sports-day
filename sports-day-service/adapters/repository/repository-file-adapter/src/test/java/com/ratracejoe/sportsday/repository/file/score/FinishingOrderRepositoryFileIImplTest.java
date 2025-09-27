package com.ratracejoe.sportsday.repository.file.score;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FinishingOrderRepositoryFileIImplTest {
  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    FinishingOrderRepositoryFileImpl repository = new FinishingOrderRepositoryFileImpl(tempDir);
    FinishingOrder domain =
        new FinishingOrder(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()));

    // When
    String csv = repository.toCsv(domain);
    FinishingOrder retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void createAndFetch(@TempDir Path tempDir) {
    // Given
    FinishingOrderRepositoryFileImpl repository = new FinishingOrderRepositoryFileImpl(tempDir);
    FinishingOrder finishingOrder =
        new FinishingOrder(UUID.randomUUID(), List.of(UUID.randomUUID(), UUID.randomUUID()));

    // When
    repository.save(finishingOrder);
    FinishingOrder found = repository.getById(finishingOrder.eventId());

    // Then
    assertThat(found).isEqualTo(finishingOrder);
  }
}
