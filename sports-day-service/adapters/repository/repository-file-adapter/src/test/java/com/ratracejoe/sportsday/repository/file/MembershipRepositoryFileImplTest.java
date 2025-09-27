package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MembershipRepositoryFileImplTest {
  @Test
  void membership(@TempDir Path tempDir) {
    // Given
    MembershipRepositoryFileImpl repository = new MembershipRepositoryFileImpl(tempDir);
    UUID team1 = UUID.randomUUID();
    UUID team1MemberA = UUID.randomUUID();
    UUID team1MemberB = UUID.randomUUID();
    UUID team1MemberC = UUID.randomUUID();
    UUID team2 = UUID.randomUUID();
    UUID team2MemberA = UUID.randomUUID();
    UUID team2MemberB = UUID.randomUUID();
    UUID memberOfBoth = UUID.randomUUID();

    // When
    repository.addMembership(team1, team1MemberA);
    repository.addMembership(team1, team1MemberB);
    repository.addMembership(team1, team1MemberC);
    repository.addMembership(team1, memberOfBoth);
    repository.addMembership(team2, team2MemberA);
    repository.addMembership(team2, team2MemberB);
    repository.addMembership(team2, memberOfBoth);
    List<UUID> team1Members = repository.getMemberIds(team1);
    List<UUID> team2Members = repository.getMemberIds(team2);

    // Then
    assertThat(team1Members)
        .containsExactlyInAnyOrder(team1MemberA, team1MemberB, team1MemberC, memberOfBoth);
    assertThat(team2Members).containsExactlyInAnyOrder(team2MemberA, team2MemberB, memberOfBoth);
  }
}
