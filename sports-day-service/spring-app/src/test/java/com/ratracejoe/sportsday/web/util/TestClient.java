package com.ratracejoe.sportsday.web.util;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.rest.auth.model.LoginRequestDTO;
import com.ratracejoe.sportsday.rest.auth.model.LoginResponseDTO;
import com.ratracejoe.sportsday.rest.model.*;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestClient {
  @Autowired private TestRestTemplate restTemplate;

  private ResponseEntity<LoginResponseDTO> loginResponse;

  public void callLogin(LoginRequestDTO login) {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("username", login.username());
    body.add("password", login.password());
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, new HttpHeaders());
    loginResponse =
        restTemplate.exchange("/auth/login", HttpMethod.POST, request, LoginResponseDTO.class);
  }

  public HttpHeaders getLoggedInHeaders() {
    assertThat(loginResponse).isNotNull();
    assertThat(loginResponse.getBody()).isNotNull();
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(loginResponse.getBody().accessToken());
    return headers;
  }

  public ResponseEntity<List<ActivityDTO>> callGetAllActivities() {
    return callGetAll(ACTIVITY_URL);
  }

  public ResponseEntity<ActivityDTO> callGetActivity(UUID id) {
    return callGet(ACTIVITY_URL, ActivityDTO.class, id);
  }

  public ResponseEntity<ActivityDTO> callCreateActivity(NewActivityDTO dto) {
    HttpEntity<NewActivityDTO> request = new HttpEntity<>(dto, getLoggedInHeaders());
    return restTemplate.exchange(ACTIVITY_URL, HttpMethod.POST, request, ActivityDTO.class);
  }

  public ResponseEntity<Void> callDeleteActivity(UUID id) {
    return callDelete(ACTIVITY_URL, id);
  }

  public ResponseEntity<List<TeamDTO>> callGetAllTeams() {
    return callGetAll(TEAM_URL);
  }

  public ResponseEntity<TeamDTO> callGetTeam(UUID id) {
    return callGet(TEAM_URL, TeamDTO.class, id);
  }

  public ResponseEntity<TeamDTO> callCreateTeam(NewTeamDTO dto) {
    HttpEntity<NewTeamDTO> request = new HttpEntity<>(dto, getLoggedInHeaders());
    return restTemplate.exchange(TEAM_URL, HttpMethod.POST, request, TeamDTO.class);
  }

  public ResponseEntity<TeamDTO> callRegisterMember(UUID teamId, UUID competitorId) {
    HttpEntity<Void> request = new HttpEntity<>(null, getLoggedInHeaders());
    String url = String.format("%s/%s/registerMember/%s", TEAM_URL, teamId, competitorId);
    return restTemplate.exchange(url, HttpMethod.POST, request, TeamDTO.class);
  }

  public ResponseEntity<Void> callDeleteTeam(UUID id) {
    return callDelete(TEAM_URL, id);
  }

  public ResponseEntity<List<CompetitorDTO>> callGetAllCompetitors() {
    return callGetAll(COMPETITOR_URL);
  }

  public ResponseEntity<CompetitorDTO> callGetCompetitor(UUID id) {
    return callGet(COMPETITOR_URL, CompetitorDTO.class, id);
  }

  public ResponseEntity<CompetitorDTO> callCreateCompetitor(NewCompetitorDTO dto) {
    HttpEntity<NewCompetitorDTO> request = new HttpEntity<>(dto, getLoggedInHeaders());
    return restTemplate.exchange(COMPETITOR_URL, HttpMethod.POST, request, CompetitorDTO.class);
  }

  public ResponseEntity<Void> callDeleteCompetitor(UUID id) {
    return callDelete(COMPETITOR_URL, id);
  }

  public ResponseEntity<List<EventDTO>> callGetAllEvents() {
    return callGetAll(EVENT_URL);
  }

  public ResponseEntity<EventDTO> callGetEvent(UUID id) {
    return callGet(EVENT_URL, EventDTO.class, id);
  }

  public ResponseEntity<EventDTO> callCreateEvent(NewEventDTO dto) {
    HttpEntity<NewEventDTO> request = new HttpEntity<>(dto, getLoggedInHeaders());
    return restTemplate.exchange(EVENT_URL, HttpMethod.POST, request, EventDTO.class);
  }

  public ResponseEntity<EventDTO> callRegisterParticipant(UUID eventId, UUID participantId) {
    HttpEntity<Void> request = new HttpEntity<>(null, getLoggedInHeaders());
    String url = String.format("%s/%s/registerParticipant/%s", EVENT_URL, eventId, participantId);
    return restTemplate.exchange(url, HttpMethod.POST, request, EventDTO.class);
  }

  public ResponseEntity<Void> callDeleteEvent(UUID id) {
    return callDelete(EVENT_URL, id);
  }

  private <T> ResponseEntity<T> callGet(String baseUrl, Class<T> clazz, UUID id) {
    HttpEntity<Void> request = new HttpEntity<>(getLoggedInHeaders());
    return restTemplate.exchange(getIdUrl(baseUrl, id), HttpMethod.GET, request, clazz);
  }

  private <T> ResponseEntity<List<T>> callGetAll(String baseUrl) {
    HttpEntity<Void> request = new HttpEntity<>(getLoggedInHeaders());
    return restTemplate.exchange(
        baseUrl, HttpMethod.GET, request, new ParameterizedTypeReference<>() {});
  }

  private ResponseEntity<Void> callDelete(String baseUrl, UUID id) {
    HttpEntity<Void> request = new HttpEntity<>(getLoggedInHeaders());
    return restTemplate.exchange(getIdUrl(baseUrl, id), HttpMethod.DELETE, request, Void.class);
  }

  private static final String ACTIVITY_URL = "/activities";
  private static final String TEAM_URL = "/teams";
  private static final String COMPETITOR_URL = "/competitors";
  private static final String EVENT_URL = "/events";

  private String getIdUrl(String base, UUID id) {
    return String.format("%s/%s", base, id);
  }
}
