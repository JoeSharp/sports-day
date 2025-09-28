package com.ratracejoe.sportsday.web.services;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.rest.auth.model.LoginRequestDTO;
import com.ratracejoe.sportsday.rest.model.NewTeamDTO;
import com.ratracejoe.sportsday.rest.model.TeamDTO;
import com.ratracejoe.sportsday.web.util.SportsDayContainers;
import com.ratracejoe.sportsday.web.util.TestClient;
import com.ratracejoe.sportsday.web.util.TestUser;
import io.cucumber.java.AfterAll;
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

  @Test
  void createTeam() {
    // Given
    String teamName = "Pogo Champions";
    TestUser user = TestUser.JOE;
    testClient.callLogin(new LoginRequestDTO(user.getUsername(), user.getPassword()));

    // When
    ResponseEntity<TeamDTO> created = testClient.callCreateTeam(new NewTeamDTO(teamName));
    ResponseEntity<TeamDTO> found = testClient.callGetTeam(created.getBody().id());

    // Then
    assertThat(found)
        .extracting(ResponseEntity::getBody)
        .extracting(TeamDTO::name)
        .isEqualTo(teamName);
  }
}
