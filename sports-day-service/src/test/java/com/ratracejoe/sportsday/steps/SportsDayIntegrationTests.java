package com.ratracejoe.sportsday.steps;

import static com.ratracejoe.sportsday.service.AuditService.AUDIT_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.model.rest.ActivityDTO;
import com.ratracejoe.sportsday.util.KeycloakExtension;
import com.redis.testcontainers.RedisContainer;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SportsDayIntegrationTests {
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private KafkaTemplate<String, String> kafkaProducer;

  private static final KeycloakExtension keycloakExtension = new KeycloakExtension();

  private static final ConfluentKafkaContainer kafka =
      new ConfluentKafkaContainer("confluentinc/cp-kafka:7.4.0");

  private static final int REDIS_PORT = 6379;

  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  private static final List<String> auditsReceived = new ArrayList<>();

  @KafkaListener(topics = "audit", groupId = "testGroup")
  public static void processMessage(String content) {
    auditsReceived.add(content);
  }

  /**
   * It's a bit odd, but I think that the Cucumber runner is overriding the lifecycle methods used
   * by JUnit 5 to initialise Extensions, and the Testcontainers annotation. So here we are
   * overriding those Cucumber lifecycle methods and manually setting up those things.
   */
  @BeforeAll
  public static void beforeAll() {
    kafka.start();
    redis.start();
    keycloakExtension.beforeAll();
  }

  @AfterAll
  public static void afterAll() {
    kafka.stop();
    redis.stop();
    keycloakExtension.afterAll();
  }

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add(
        "spring.security.oauth2.resourceserver.jwt.issuer-uri", keycloakExtension::getIssuerUri);
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }

  @Given("random activity created")
  public void randomActivityCreated() {
    ActivityDTO dto =
        new ActivityDTO(
            null, String.format("Random %s", UUID.randomUUID()), "Created for test purposes");
    ResponseEntity<ActivityDTO> response = callCreateActivity(dto);
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
        .until(() -> auditsReceived.contains(msg));
  }

  @When("the client requests activities")
  public void clientRequestsActivities() {
    listResponseEntity = callGetActivities();
  }

  @When("the client requests that activity by ID")
  public void clientRequestsActivity() {
    assertThat(activityUnderTest).isNotNull();
    singleResponseEntity = callGetActivity(activityUnderTest.id());
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
    ActivityDTO dto = new ActivityDTO(null, name, description);

    singleResponseEntity = callCreateActivity(dto);
    activityUnderTest = singleResponseEntity.getBody();
    assertThat(activityUnderTest).isNotNull();
  }

  @When("the client deletes that activity by ID")
  public void theClientDeletesThatActivityByID() {
    voidResponseEntity = callDeleteActivity(activityUnderTest.id());
  }

  @And("the activity no longer exists")
  public void assertActivityDoesNotExist() {
    ResponseEntity<ActivityDTO> response = callGetActivity(activityUnderTest.id());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @And("an audit captures the reading of the list of activities")
  public void anAuditCapturesTheReadingOfTheListOfActivities() {
    assertThat(auditsReceived).contains("Activities were read");
  }

  @And("an audit captures the {word} of that activity")
  public void anAuditCapturesThatSingleActivity(String action) {
    switch (action) {
      case "read":
        assertThat(auditsReceived).contains(String.format("Activity %s read", activityUnderTest.id()));
        break;
      case "deletion":
        assertThat(auditsReceived)
                .contains(String.format("Activity %s deleted", activityUnderTest.id()));
        break;
      case "creation":
        var startsWith = String.format("Activity %s created with ID", activityUnderTest.name());
        assertThat(auditsReceived).anyMatch(l -> l.startsWith(startsWith));
        break;
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

  private String getActivityIdUrl(UUID id) {
    return String.format("/activities/%s", id);
  }

  private ResponseEntity<List<ActivityDTO>> callGetActivities() {
    HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
    return restTemplate.exchange(
            "/activities", HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
  }

  private ResponseEntity<ActivityDTO> callGetActivity(UUID id) {
    HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
    return restTemplate.exchange(
            getActivityIdUrl(id), HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
  }

  private ResponseEntity<ActivityDTO> callCreateActivity(ActivityDTO dto) {
    HttpEntity<ActivityDTO> request = new HttpEntity<>(dto, keycloakExtension.getAuthHeaders());
    return restTemplate.exchange("/activities", HttpMethod.POST, request, ActivityDTO.class);
  }

  private ResponseEntity<Void> callDeleteActivity(UUID id) {
    HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
    return restTemplate.exchange(
            getActivityIdUrl(id), HttpMethod.DELETE, request, new ParameterizedTypeReference<>() {});
  }

}
