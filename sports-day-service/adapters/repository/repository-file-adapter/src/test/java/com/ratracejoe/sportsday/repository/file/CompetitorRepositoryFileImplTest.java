package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CompetitorRepositoryFileImplTest {
  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    CompetitorRepositoryFileImpl repository = new CompetitorRepositoryFileImpl(tempDir);
    Competitor domain = new Competitor(UUID.randomUUID(), CompetitorType.INDIVIDUAL, "Mr Blobby");

    // When
    String csv = repository.toCsv(domain);
    Competitor retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void createAndGet(@TempDir Path tempDir) {
    // Given
    CompetitorRepositoryFileImpl repository = new CompetitorRepositoryFileImpl(tempDir);
    Competitor competitor =
        new Competitor(UUID.randomUUID(), CompetitorType.INDIVIDUAL, "Mr Blobby");

    // When
    repository.save(competitor);
    Competitor found = repository.getById(competitor.id());

    // Then
    assertThat(found).isEqualTo(competitor);
  }
}
