package com.ratracejoe.sportsday.repository.file;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ParticipantRepositoryFileImplTest {
  @Test
  void participation(@TempDir Path tempDir) {
    // Given
    ParticipantRepositoryFileImpl repository = new ParticipantRepositoryFileImpl(tempDir);
    UUID event1 = UUID.randomUUID();
    UUID event1MemberA = UUID.randomUUID();
    UUID event1MemberB = UUID.randomUUID();
    UUID event1MemberC = UUID.randomUUID();
    UUID event2 = UUID.randomUUID();
    UUID event2MemberA = UUID.randomUUID();
    UUID event2MemberB = UUID.randomUUID();
    UUID memberOfBoth = UUID.randomUUID();

    // When
    repository.addParticipant(event1, event1MemberA);
    repository.addParticipant(event1, event1MemberB);
    repository.addParticipant(event1, event1MemberC);
    repository.addParticipant(event1, memberOfBoth);
    repository.addParticipant(event2, event2MemberA);
    repository.addParticipant(event2, event2MemberB);
    repository.addParticipant(event2, memberOfBoth);
    List<UUID> event1Members = repository.getParticipants(event1);
    List<UUID> event2Members = repository.getParticipants(event2);

    // Then
    assertThat(event1Members)
        .containsExactlyInAnyOrder(event1MemberA, event1MemberB, event1MemberC, memberOfBoth);
    assertThat(event2Members).containsExactlyInAnyOrder(event2MemberA, event2MemberB, memberOfBoth);
  }
}
