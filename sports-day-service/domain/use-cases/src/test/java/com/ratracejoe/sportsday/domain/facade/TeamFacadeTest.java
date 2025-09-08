package com.ratracejoe.sportsday.domain.facade;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.FixtureFactory;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.domain.outgoing.MemoryAuditLogger;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamFacadeTest {
  private TeamFacade teamFacade;
  private CompetitorFacade competitorFacade;
  private MemoryAuditLogger auditLogger;

  @BeforeEach
  void beforeEach() {
    FixtureFactory fixtures = new FixtureFactory();
    auditLogger = fixtures.auditLogger();
    teamFacade = fixtures.teamFacade();
    competitorFacade = fixtures.competitorFacade();
  }

  @Test
  void createTeam() {
    // Given
    String teamName = "The Hamsters";

    // When
    Team team = teamFacade.createTeam(teamName);

    // Then
    assertThat(team).extracting(Team::id).isNotNull();
    assertThat(team).isNotNull().extracting(Team::name).isEqualTo(teamName);
    assertThat(auditLogger.getMessages())
        .containsExactly("Team 'The Hamsters' created with ID: " + team.id());
  }

  @Test
  void getById() throws NotFoundException {
    // Given
    Team team = teamFacade.createTeam("The Snakes");

    // When
    Team found = teamFacade.getById(team.id());

    // Then
    assertThat(found).isEqualTo(team);
    assertThat(auditLogger.getMessages())
        .containsExactly("Team 'The Snakes' created with ID: " + team.id());
  }

  @Test
  void registerAndRetrieveMembers() throws NotFoundException {
    // Given
    Team team = teamFacade.createTeam("The Eagles");
    Competitor memberA = competitorFacade.createCompetitor("Adam Zebra");
    Competitor memberB = competitorFacade.createCompetitor("Billy Yoghurt");
    competitorFacade.createCompetitor("Charlie Wurgle");

    // When
    teamFacade.registerMember(team.id(), memberA.id());
    teamFacade.registerMember(team.id(), memberB.id());
    List<Competitor> members = teamFacade.getMembers(team.id());

    // Then
    assertThat(members).containsExactlyInAnyOrder(memberA, memberB);
    assertThat(auditLogger.getMessages())
        .containsExactly("Team 'The Eagles' created with ID: " + team.id());
  }
}
