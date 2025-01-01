package com.ratracejoe.sportsday.controller;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.util.KeycloakExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActivityControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @RegisterExtension
    private static KeycloakExtension keycloakExtension = new KeycloakExtension();

    @DynamicPropertySource
    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                keycloakExtension::getIssuerUri);
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
    }
}
