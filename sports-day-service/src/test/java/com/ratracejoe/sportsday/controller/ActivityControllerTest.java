package com.ratracejoe.sportsday.controller;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.util.KeycloakExtension;
import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class ActivityControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @RegisterExtension
    private static KeycloakExtension keycloakExtension = new KeycloakExtension();

    private final List<String> auditsReceived = new ArrayList<>();

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
            new RedisContainer(DockerImageName.parse("redis"))
                    .withExposedPorts(REDIS_PORT);

    @KafkaListener(topics = "audit", groupId = "myGroup")
    public void processMessage(String content) {
        auditsReceived.add(content);
    }

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                keycloakExtension::getIssuerUri);
        registry.add("spring.kafka.bootstrap-servers",
                kafka::getBootstrapServers);
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port",
                () -> redis.getMappedPort(REDIS_PORT));
    }

    @Test
    void getActivities() {
        HttpEntity<Void> request = new HttpEntity<>(keycloakExtension.getAuthHeaders());
        ResponseEntity<List<ActivityDTO>> response =
                restTemplate.exchange("/activities", HttpMethod.GET,
                    request,
                    new ParameterizedTypeReference<>() {
                    });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var activities = response.getBody();

        assertThat(activities).anyMatch(a -> "Running".equals(a.name()));
        assertThat(auditsReceived).contains("Activities were read");
    }

    @Test
    void createActivity() {
        ActivityDTO dto = new ActivityDTO(null, "Shooting", "Pointing guns at things and going bang");
        HttpEntity<ActivityDTO> request = new HttpEntity<>(dto, keycloakExtension.getAuthHeaders());
        ResponseEntity<ActivityDTO> response =
                restTemplate.exchange("/activities", HttpMethod.POST,
                        request,
                        ActivityDTO.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var activity = response.getBody();
        assertThat(activity.id()).isNotNull();
    }
}
