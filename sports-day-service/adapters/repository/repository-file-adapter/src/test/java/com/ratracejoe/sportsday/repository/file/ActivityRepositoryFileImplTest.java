package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Activity;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ActivityRepositoryFileImplTest {

  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    ActivityRepositoryFileImpl repository = new ActivityRepositoryFileImpl(tempDir);
    Activity domain =
        new Activity(UUID.randomUUID(), "Swimming", "Propelling oneself through water innit");

    // When
    String csv = repository.toCsv(domain);
    Activity retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void saveAndGet(@TempDir Path tempDir) {
    // Given
    ActivityRepositoryFileImpl repository = new ActivityRepositoryFileImpl(tempDir);
    Activity activity =
        new Activity(UUID.randomUUID(), "Swimming", "Propelling oneself through water innit");

    // When
    repository.save(activity);
    Activity found = repository.getById(activity.id());

    // Then
    assertThat(found).isEqualTo(activity);
  }
}
