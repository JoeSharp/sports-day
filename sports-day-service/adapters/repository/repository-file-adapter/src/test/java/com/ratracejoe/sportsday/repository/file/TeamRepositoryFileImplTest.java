package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Team;
import java.nio.file.Path;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TeamRepositoryFileImplTest {
  @Test
  void toFromCsv(@TempDir Path tempDir) {
    // Given
    TeamRepositoryFileImpl repository = new TeamRepositoryFileImpl(tempDir);
    Team domain = new Team(UUID.randomUUID(), "The Rats");

    // When
    String csv = repository.toCsv(domain);
    Team retrieved = repository.fromCsv(csv);

    // Then
    assertThat(retrieved).isEqualTo(domain);
  }

  @Test
  void createAndGet(@TempDir Path tempDir) {
    // Given
    TeamRepositoryFileImpl repository = new TeamRepositoryFileImpl(tempDir);
    Team team = new Team(UUID.randomUUID(), "The Rats");

    // When
    repository.save(team);
    Team found = repository.getById(team.id());

    // Then
    assertThat(found).isEqualTo(team);
  }
}
