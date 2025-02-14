package com.ratracejoe.sportsday.controller;

import static com.ratracejoe.sportsday.service.AuditService.AUDIT_TOPIC;
import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.model.rest.ActivityDTO;
import com.ratracejoe.sportsday.util.KeycloakExtension;
import com.redis.testcontainers.RedisContainer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.shaded.org.awaitility.Awaitility;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ActivityControllerTest {
  @Autowired private TestRestTemplate restTemplate;

  @Autowired private KafkaTemplate<String, String> kafkaProducer;

  @RegisterExtension
  private static final KeycloakExtension keycloakExtension = new KeycloakExtension();

  private static final List<String> auditsReceived = new ArrayList<>();

  @BeforeEach
  void beforeEach() {
    auditsReceived.clear();
  }

  @Container
  private static final ConfluentKafkaContainer kafka =
      new ConfluentKafkaContainer("confluentinc/cp-kafka:7.4.0");

  private static final int REDIS_PORT = 6379;

  @Container
  private static final RedisContainer redis =
      new RedisContainer(DockerImageName.parse("redis")).withExposedPorts(REDIS_PORT);

  @KafkaListener(topics = "audit", groupId = "testGroup")
  public static void processMessage(String content) {
    auditsReceived.add(content);
  }

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add(
        "spring.security.oauth2.resourceserver.jwt.issuer-uri", keycloakExtension::getIssuerUri);
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", () -> redis.getMappedPort(REDIS_PORT));
  }

  @Test
  void getActivity() {
    // Given
    ActivityDTO activityDTO = randomActivityCreated();

    // When
    ResponseEntity<ActivityDTO> response = callGetActivity(activityDTO.id());

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().name()).isEqualTo(activityDTO.name());
    pipecleanAudits();
    assertThat(auditsReceived).contains(String.format("Activity %s read", activityDTO.id()));
  }

  @Test
  void getNonExistentActivity() {
    // When
    ResponseEntity<ActivityDTO> response = callGetActivity(UUID.randomUUID());

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void getActivities() {
    // When
    ResponseEntity<List<ActivityDTO>> response = callGetActivities();

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var activities = response.getBody();

    assertThat(activities).anyMatch(a -> "Running".equals(a.name()));
    pipecleanAudits();
    assertThat(auditsReceived).contains("Activities were read");
  }

  @Test
  void createActivity() {
    // When
    ActivityDTO dto = new ActivityDTO(null, "Shooting", "Pointing guns at things and going bang");
    ResponseEntity<ActivityDTO> response = callCreateActivity(dto);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    var activity = response.getBody();
    assertThat(activity).isNotNull();
    assertThat(activity.id()).isNotNull();
    pipecleanAudits();
    assertThat(auditsReceived).anyMatch(l -> l.startsWith("Activity Shooting created with ID"));
  }

  @Test
  void deleteActivity() {
    // Given
    ActivityDTO activityDTO = randomActivityCreated();

    // When
    ResponseEntity<Void> response = callDeleteActivity(activityDTO.id());

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertActivityDoesNotExist(activityDTO);
    pipecleanAudits();
    assertThat(auditsReceived).contains(String.format("Activity %s read", activityDTO.id()));
    assertThat(auditsReceived).contains(String.format("Activity %s deleted", activityDTO.id()));
  }

  @Test
  void deleteNonExistentActivity() {
    // When
    ResponseEntity<Void> response = callDeleteActivity(UUID.randomUUID());

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private ActivityDTO randomActivityCreated() {
    ActivityDTO dto =
        new ActivityDTO(
            null, String.format("Random %s", UUID.randomUUID()), "Created for test purposes");
    ResponseEntity<ActivityDTO> response = callCreateActivity(dto);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    return response.getBody();
  }

  private void assertActivityDoesNotExist(ActivityDTO activityDTO) {
    ResponseEntity<ActivityDTO> response = callGetActivity(activityDTO.id());
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  private ResponseEntity<List<ActivityDTO>> callGetActivities() {
    HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
    return restTemplate.exchange(
        "/activities", HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
  }

  private String getActivityIdUrl(UUID id) {
    return String.format("/activities/%s", id);
  }

  private ResponseEntity<ActivityDTO> callGetActivity(UUID id) {
    HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
    return restTemplate.exchange(
        getActivityIdUrl(id), HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
  }

  private ResponseEntity<Void> callDeleteActivity(UUID id) {
    HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
    return restTemplate.exchange(
        getActivityIdUrl(id), HttpMethod.DELETE, request, new ParameterizedTypeReference<>() {});
  }

  private ResponseEntity<ActivityDTO> callCreateActivity(ActivityDTO dto) {
    HttpEntity<ActivityDTO> request = new HttpEntity<>(dto, keycloakExtension.getAuthHeaders());
    return restTemplate.exchange("/activities", HttpMethod.POST, request, ActivityDTO.class);
  }

  private void pipecleanAudits() {
    String msg = UUID.randomUUID().toString();
    kafkaProducer.send(AUDIT_TOPIC, msg);

    Awaitility.await()
        .atMost(Duration.ofSeconds(5))
        .pollInterval(Duration.ofSeconds(1))
        .until(() -> auditsReceived.contains(msg));
  }
}
