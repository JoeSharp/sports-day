package com.ratracejoe.sportsday.repository.cache;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.repository.memory.MemoryTeamRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class CachedRepositoryTest {

  @Test
  void getAllCached() {
    // Given
    MemoryTeamRepository slowRepo = new MemoryTeamRepository();
    Stream.of("Alphas", "Bravos", "Charlies")
        .map(name -> new Team(UUID.randomUUID(), name))
        .forEach(slowRepo::save);
    MemoryTeamRepository fastRepo = new MemoryTeamRepository();
    TeamCachedRepository cachedRepo = new TeamCachedRepository(slowRepo, fastRepo);

    // When
    List<Team> results1 = cachedRepo.getAll();
    List<Team> results2 = cachedRepo.getAll();
    List<Team> results3 = cachedRepo.getAll();

    // Then
    assertThat(slowRepo).extracting(MemoryTeamRepository::getCallsToGetAll).isEqualTo(1);
    assertThat(fastRepo).extracting(MemoryTeamRepository::getCallsToGetAll).isEqualTo(3);
    assertThat(results1)
        .isEqualTo(results2)
        .isEqualTo(results3)
        .extracting(Team::name)
        .containsExactlyInAnyOrder("Alphas", "Bravos", "Charlies");
  }

  @Test
  void saveWriteThrough() {
    // Given
    MemoryTeamRepository slowRepo = new MemoryTeamRepository();
    MemoryTeamRepository fastRepo = new MemoryTeamRepository();
    TeamCachedRepository cachedRepo = new TeamCachedRepository(slowRepo, fastRepo);

    Stream.of("Xenon", "Yacto", "Zebra")
        .map(name -> new Team(UUID.randomUUID(), name))
        .forEach(cachedRepo::save);

    // When
    List<Team> resultsCached = cachedRepo.getAll();
    List<Team> resultsFast = fastRepo.getAll();

    // Then
    assertThat(slowRepo).extracting(MemoryTeamRepository::getCallsToGetAll).isEqualTo(0);
    assertThat(fastRepo).extracting(MemoryTeamRepository::getCallsToGetAll).isEqualTo(2);
    assertThat(resultsCached)
        .isEqualTo(resultsFast)
        .extracting(Team::name)
        .containsExactlyInAnyOrder("Xenon", "Yacto", "Zebra");
  }

  @Test
  void deleteWriteThrough() {
    // Given
    MemoryTeamRepository slowRepo = new MemoryTeamRepository();
    MemoryTeamRepository fastRepo = new MemoryTeamRepository();
    TeamCachedRepository cachedRepo = new TeamCachedRepository(slowRepo, fastRepo);

    List<Team> teams =
        Stream.of("Richter", "Shirley", "Tango")
            .map(name -> new Team(UUID.randomUUID(), name))
            .toList();
    teams.forEach(cachedRepo::save);
    Team shirley = teams.get(1);

    // When
    cachedRepo.deleteById(shirley.id());
    List<Team> resultsCached = cachedRepo.getAll();

    // Then
    List<UUID> expectedDeletions = List.of(shirley.id());
    assertThat(slowRepo)
        .extracting(MemoryTeamRepository::getCallsToDelete)
        .isEqualTo(expectedDeletions);
    assertThat(fastRepo)
        .extracting(MemoryTeamRepository::getCallsToDelete)
        .isEqualTo(expectedDeletions);
    assertThat(resultsCached).extracting(Team::name).containsExactlyInAnyOrder("Richter", "Tango");
  }
}
