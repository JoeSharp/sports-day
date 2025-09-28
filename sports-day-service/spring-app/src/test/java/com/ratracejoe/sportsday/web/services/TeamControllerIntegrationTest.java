package com.ratracejoe.sportsday.web.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.rest.auth.model.LoginRequestDTO;
import com.ratracejoe.sportsday.rest.model.CompetitorDTO;
import com.ratracejoe.sportsday.rest.model.NewCompetitorDTO;
import com.ratracejoe.sportsday.rest.model.NewTeamDTO;
import com.ratracejoe.sportsday.rest.model.TeamDTO;
import com.ratracejoe.sportsday.web.util.SportsDayContainers;
import com.ratracejoe.sportsday.web.util.TestClient;
import com.ratracejoe.sportsday.web.util.TestUser;
import io.cucumber.java.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestClient.class)
public class TeamControllerIntegrationTest {
  @Autowired private TestClient testClient;

  @Autowired private KafkaTemplate<String, String> kafkaProducer;

  @KafkaListener(topics = "audit", groupId = "testGroup")
  public static void processMessage(String content) {
    SportsDayContainers.processMessage(content);
  }

  static {
    SportsDayContainers.beforeAll();
  }

  @AfterAll
  public static void afterAll() {
    SportsDayContainers.afterAll();
  }

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    SportsDayContainers.registerDynamicProperties(registry);
  }

  @BeforeEach
  void beforeEach() {
    TestUser user = TestUser.JOE;
    testClient.callLogin(new LoginRequestDTO(user.getUsername(), user.getPassword()));
  }

  @Test
  void createTeam() {
    // Given
    String teamName = "Pogo Champions";

    // When
    ResponseEntity<TeamDTO> created = testClient.callCreateTeam(new NewTeamDTO(teamName));
    ResponseEntity<TeamDTO> found = testClient.callGetTeam(created.getBody().id());

    // Then
    assertThat(found)
        .extracting(ResponseEntity::getBody)
        .extracting(TeamDTO::name)
        .isEqualTo(teamName);
  }

  @Test
  void registerMembers() {
    // Given
    String teamName = "Boys from the Dwarf";
    String memberName1 = "Dave Lister";
    String memberName2 = "Arnold Rimmer";
    String memberName3 = "The Cat";
    String memberName4 = "Kryten";

    ResponseEntity<TeamDTO> team = testClient.callCreateTeam(new NewTeamDTO(teamName));
    ResponseEntity<CompetitorDTO> member1 =
        testClient.callCreateCompetitor(
            new NewCompetitorDTO(CompetitorType.INDIVIDUAL, memberName1));
    ResponseEntity<CompetitorDTO> member2 =
        testClient.callCreateCompetitor(
            new NewCompetitorDTO(CompetitorType.INDIVIDUAL, memberName2));
    ResponseEntity<CompetitorDTO> member3 =
        testClient.callCreateCompetitor(
            new NewCompetitorDTO(CompetitorType.INDIVIDUAL, memberName3));
    ResponseEntity<CompetitorDTO> member4 =
        testClient.callCreateCompetitor(
            new NewCompetitorDTO(CompetitorType.INDIVIDUAL, memberName4));
    assertThat(team.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(member1.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(member2.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(member3.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(member4.getStatusCode().is2xxSuccessful()).isTrue();

    // When
    // Deliberately leave Rimmer out, he likes Morris Dancing
    testClient.callRegisterMember(team.getBody().id(), member1.getBody().id());
    testClient.callRegisterMember(team.getBody().id(), member3.getBody().id());
    testClient.callRegisterMember(team.getBody().id(), member4.getBody().id());
    ResponseEntity<TeamDTO> teamAfter = testClient.callGetTeam(team.getBody().id());

    // Then
    assertThat(teamAfter.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(teamAfter.getBody().members())
        .containsExactlyInAnyOrder(member1.getBody(), member3.getBody(), member4.getBody())
        .doesNotContain(member2.getBody());
  }
}
