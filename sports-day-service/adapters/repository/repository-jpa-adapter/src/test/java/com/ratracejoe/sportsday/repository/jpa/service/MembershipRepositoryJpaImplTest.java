package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MembershipRepositoryJpaImplTest {
  @Autowired MembershipRepositoryJpaImpl membershipRepository;

  @Test
  void membership() {
    // Given
    UUID team1 = UUID.randomUUID();
    UUID team1MemberA = UUID.randomUUID();
    UUID team1MemberB = UUID.randomUUID();
    UUID team1MemberC = UUID.randomUUID();
    UUID team2 = UUID.randomUUID();
    UUID team2MemberA = UUID.randomUUID();
    UUID team2MemberB = UUID.randomUUID();
    UUID memberOfBoth = UUID.randomUUID();

    // When
    membershipRepository.addMembership(team1, team1MemberA);
    membershipRepository.addMembership(team1, team1MemberB);
    membershipRepository.addMembership(team1, team1MemberC);
    membershipRepository.addMembership(team1, memberOfBoth);
    membershipRepository.addMembership(team2, team2MemberA);
    membershipRepository.addMembership(team2, team2MemberB);
    membershipRepository.addMembership(team2, memberOfBoth);
    List<UUID> team1Members = membershipRepository.getMemberIds(team1);
    List<UUID> team2Members = membershipRepository.getMemberIds(team2);

    // Then
    assertThat(team1Members)
        .containsExactlyInAnyOrder(team1MemberA, team1MemberB, team1MemberC, memberOfBoth);
    assertThat(team2Members).containsExactlyInAnyOrder(team2MemberA, team2MemberB, memberOfBoth);
  }
}
