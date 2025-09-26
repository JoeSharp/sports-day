package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Team;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TeamRepositoryJpaImplTest {
  @Autowired private TeamRepositoryJpaImpl teamRepository;

  @Test
  void createAndGet() {
    // Given
    Team team = new Team(UUID.randomUUID(), "The Rats");

    // When
    teamRepository.save(team);
    Team found = teamRepository.getById(team.id());

    // Then
    assertThat(found).isEqualTo(team);
  }
}
