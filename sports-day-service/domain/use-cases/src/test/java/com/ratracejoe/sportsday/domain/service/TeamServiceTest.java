package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamServiceTest {
  private TeamService teamService;
  private CompetitorService competitorService;
  private MemoryAuditLogger auditLogger;

  @BeforeEach
  void beforeEach() {
    MemoryAdapters fixtures = new MemoryAdapters();
    auditLogger = fixtures.auditLogger();
    teamService = fixtures.teamService();
    competitorService = fixtures.competitorService();
  }

  @Test
  void createTeam() {
    // Given
    String teamName = "The Hamsters";

    // When
    Team team = teamService.createTeam(teamName);

    // Then
    assertThat(team).extracting(Team::id).isNotNull();
    assertThat(team).isNotNull().extracting(Team::name).isEqualTo(teamName);
    assertThat(auditLogger.getMessages())
        .containsExactly("Team 'The Hamsters' created with ID: " + team.id());
  }

  @Test
  void getById() throws NotFoundException {
    // Given
    Team team = teamService.createTeam("The Snakes");

    // When
    Team found = teamService.getById(team.id());

    // Then
    assertThat(found).isEqualTo(team);
    assertThat(auditLogger.getMessages())
        .containsExactly("Team 'The Snakes' created with ID: " + team.id());
  }

  @Test
  void registerAndRetrieveMembers() throws NotFoundException {
    // Given
    Team team = teamService.createTeam("The Eagles");
    Competitor memberA = competitorService.createCompetitor("Adam Zebra");
    Competitor memberB = competitorService.createCompetitor("Billy Yoghurt");
    competitorService.createCompetitor("Charlie Wurgle");

    // When
    teamService.registerMember(team.id(), memberA.id());
    teamService.registerMember(team.id(), memberB.id());
    List<Competitor> members = teamService.getMembers(team.id());

    // Then
    assertThat(members).containsExactlyInAnyOrder(memberA, memberB);
    assertThat(auditLogger.getMessages())
        .containsExactly("Team 'The Eagles' created with ID: " + team.id());
  }
}
