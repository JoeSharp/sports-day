package com.ratracejoe.sportsday.repository.file.score;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class PointScoreSheetRepositoryFileIImplTest {
  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    PointScoreRepositoryFileImpl repository = new PointScoreRepositoryFileImpl(tempDir);
    PointScoreSheet domain =
        new PointScoreSheet(
            UUID.randomUUID(),
            Map.of(UUID.randomUUID(), 5, UUID.randomUUID(), 7, UUID.randomUUID(), 3));

    // When
    String csv = repository.toCsv(domain);
    PointScoreSheet retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void createAndFetch(@TempDir Path tempDir) {
    // Given
    PointScoreRepositoryFileImpl repository = new PointScoreRepositoryFileImpl(tempDir);
    PointScoreSheet scoreSheet =
        new PointScoreSheet(
            UUID.randomUUID(),
            Map.of(UUID.randomUUID(), 5, UUID.randomUUID(), 7, UUID.randomUUID(), 3));

    // When
    repository.save(scoreSheet);
    PointScoreSheet found = repository.getById(scoreSheet.eventId());

    // Then
    assertThat(found).isEqualTo(scoreSheet);
  }
}
