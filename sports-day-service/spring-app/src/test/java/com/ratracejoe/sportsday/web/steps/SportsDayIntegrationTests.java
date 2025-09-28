package com.ratracejoe.sportsday.web.steps;

import static com.ratracejoe.sportsday.audit.kafka.KafkaAuditLoggerImpl.AUDIT_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

import com.ratracejoe.sportsday.rest.auth.model.LoginRequestDTO;
import com.ratracejoe.sportsday.rest.model.ActivityDTO;
import com.ratracejoe.sportsday.rest.model.NewActivityDTO;
import com.ratracejoe.sportsday.web.util.SportsDayContainers;
import com.ratracejoe.sportsday.web.util.TestClient;
import com.ratracejoe.sportsday.web.util.TestUser;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import java.time.Duration;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.shaded.org.awaitility.Awaitility;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestClient.class)
public class SportsDayIntegrationTests {
  @Autowired private TestClient testClient;

  @Autowired private KafkaTemplate<String, String> kafkaProducer;

  @KafkaListener(topics = "audit", groupId = "testGroup")
  public void processMessage(String content) {
    SportsDayContainers.processMessage(content);
  }

  /**
   * It's a bit odd, but I think that the Cucumber runner is overriding the lifecycle methods used
   * by JUnit 5 to initialise Extensions, and the Testcontainers annotation. So here we are
   * overriding those Cucumber lifecycle methods and manually setting up those things.
   */
  @BeforeAll
  public static void beforeAll() {
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

  @ParameterType("JOE")
  public TestUser user(String value) {
    return TestUser.valueOf(value);
  }

  @Given("user logged in as {user}")
  public void userLoggedInAs(TestUser user) {
    LoginRequestDTO login = new LoginRequestDTO(user.getUsername(), user.getPassword());

    testClient.callLogin(login);
  }

  @Given("random activity created")
  public void randomActivityCreated() {
    NewActivityDTO dto =
        new NewActivityDTO(
            String.format("Random %s", UUID.randomUUID()), "Created for test purposes");
    ResponseEntity<ActivityDTO> response = testClient.callCreateActivity(dto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    singleResponseEntity = response;
    activityUnderTest = response.getBody();
    assertThat(activityUnderTest).isNotNull();
  }

  @And("all audits have been received")
  public void allAuditsHaveBeenReceived() {
    String msg = UUID.randomUUID().toString();
    kafkaProducer.send(AUDIT_TOPIC, msg);

    Awaitility.await()
        .atMost(Duration.ofSeconds(5))
        .pollInterval(Duration.ofSeconds(1))
        .until(() -> SportsDayContainers.getAuditsReceived().contains(msg));
  }

  @When("the client requests activities")
  public void clientRequestsActivities() {
    listResponseEntity = testClient.callGetAllActivities();
  }

  @When("the client requests that activity by ID")
  public void clientRequestsActivity() {
    assertThat(activityUnderTest).isNotNull();
    singleResponseEntity = testClient.callGetActivity(activityUnderTest.id());
  }

  @Then("the client receives list response with a status code of {int}")
  public void listResponseStatus(int expected) {
    assertThat(listResponseEntity.getStatusCode().value()).isEqualTo(expected);
  }

  @Then("the client receives single response with a status code of {int}")
  public void singleResponseStatus(int expected) {
    assertThat(singleResponseEntity.getStatusCode().value()).isEqualTo(expected);
  }

  @Then("the client receives void response with a status code of {int}")
  public void voidResponseStatus(int expected) {
    assertThat(voidResponseEntity.getStatusCode().value()).isEqualTo(expected);
  }

  @When("the client creates an activity {word} with random description")
  public void theClientCreatesAnActivityWithRandomDescription(String namePrefix) {
    String name = String.format("%s_%s", namePrefix, UUID.randomUUID());
    String description = String.format("DescribedBy_%s", UUID.randomUUID());
    NewActivityDTO dto = new NewActivityDTO(name, description);

    singleResponseEntity = testClient.callCreateActivity(dto);
    activityUnderTest = singleResponseEntity.getBody();
    assertThat(activityUnderTest).isNotNull();
  }

  @When("the client deletes that activity by ID")
  public void theClientDeletesThatActivityByID() {
    voidResponseEntity = testClient.callDeleteActivity(activityUnderTest.id());
  }

  @And("the activity no longer exists")
  public void assertActivityDoesNotExist() {
    ResponseEntity<ActivityDTO> response = testClient.callGetActivity(activityUnderTest.id());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @And("an audit captures the reading of the list of activities")
  public void anAuditCapturesTheReadingOfTheListOfActivities() {
    assertThat(SportsDayContainers.getAuditsReceived()).contains("Activities were read");
  }

  @And("an audit captures the failed read")
  public void anAuditCapturesTheFailedRead() {
    assertThat(SportsDayContainers.getAuditsReceived())
        .contains(String.format("Failed to read Activity %s", activityUnderTest.id()));
  }

  @And("an audit captures the failed deletion")
  public void anAuditCapturesTheFailedDeletion() {
    assertThat(SportsDayContainers.getAuditsReceived())
        .contains(String.format("Failed to delete Activity %s", activityUnderTest.id()));
  }

  @And("an audit captures the {word} of that activity")
  public void anAuditCapturesThatSingleActivity(String action) {
    switch (action) {
      case "read":
        assertThat(SportsDayContainers.getAuditsReceived())
            .contains(String.format("Activity %s read", activityUnderTest.id()));
        break;
      case "deletion":
        assertThat(SportsDayContainers.getAuditsReceived())
            .contains(String.format("Activity %s deleted", activityUnderTest.id()));
        break;
      case "creation":
        var startsWith = String.format("Activity '%s' created with ID", activityUnderTest.name());
        assertThat(SportsDayContainers.getAuditsReceived()).anyMatch(l -> l.startsWith(startsWith));
        break;
      default:
        fail("Invalid action to capture for audit {}", action);
    }
  }

  @Given("random ID is generated")
  public void randomIDIsGenerated() {
    activityUnderTest =
        new ActivityDTO(UUID.randomUUID(), "Nonsense", "Created client side for test purposes");
  }

  private ActivityDTO activityUnderTest;
  private ResponseEntity<List<ActivityDTO>> listResponseEntity;
  private ResponseEntity<ActivityDTO> singleResponseEntity;
  private ResponseEntity<Void> voidResponseEntity;
}
