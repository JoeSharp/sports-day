package com.ratracejoe.sportsday.controller;

import com.ratracejoe.sportsday.model.ActivityDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActivityControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getActivities() {
        ResponseEntity<List<ActivityDTO>> response =
                restTemplate.exchange("/activities", HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {
                    });

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        var activities = response.getBody();

        assertThat(activities).anyMatch(a -> "Running".equals(a.name()));
    }
}
