package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ParticipantRepositoryJpaImplTest {
  @Autowired ParticipantRepositoryJpaImpl participantRepository;

  @Test
  void participation() {
    // Given
    UUID event1 = UUID.randomUUID();
    UUID event1MemberA = UUID.randomUUID();
    UUID event1MemberB = UUID.randomUUID();
    UUID event1MemberC = UUID.randomUUID();
    UUID event2 = UUID.randomUUID();
    UUID event2MemberA = UUID.randomUUID();
    UUID event2MemberB = UUID.randomUUID();
    UUID memberOfBoth = UUID.randomUUID();

    // When
    participantRepository.addParticipant(event1, event1MemberA);
    participantRepository.addParticipant(event1, event1MemberB);
    participantRepository.addParticipant(event1, event1MemberC);
    participantRepository.addParticipant(event1, memberOfBoth);
    participantRepository.addParticipant(event2, event2MemberA);
    participantRepository.addParticipant(event2, event2MemberB);
    participantRepository.addParticipant(event2, memberOfBoth);
    List<UUID> event1Members = participantRepository.getParticipants(event1);
    List<UUID> event2Members = participantRepository.getParticipants(event2);

    // Then
    assertThat(event1Members)
        .containsExactlyInAnyOrder(event1MemberA, event1MemberB, event1MemberC, memberOfBoth);
    assertThat(event2Members).containsExactlyInAnyOrder(event2MemberA, event2MemberB, memberOfBoth);
  }
}
