package com.ratracejoe.sportsday.web.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ratracejoe.sportsday.web.exception.InvalidAuthException;
import com.ratracejoe.sportsday.web.model.rest.LoginRequestDTO;
import com.ratracejoe.sportsday.web.model.rest.LoginResponseDTO;
import com.ratracejoe.sportsday.web.model.rest.RefreshRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
  Logger logger = LoggerFactory.getLogger(AuthController.class);

  private final String clientId;
  private final String clientSecret;
  private final RestClient keycloakClient;

  public AuthController(
      @Qualifier("keycloak") RestClient keycloakClient,
      @Value("${spring.security.oauth2.client.registration.keycloak.clientId}") String clientId,
      @Value("${spring.security.oauth2.client.registration.keycloak.clientSecret}")
          String clientSecret) {
    this.keycloakClient = keycloakClient;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
  }

  @PostMapping(
      value = "/login",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public LoginResponseDTO login(LoginRequestDTO request) throws InvalidAuthException {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("username", request.username());
    body.add("password", request.password());
    body.add("grant_type", "password");

    return getToken(body);
  }

  @PostMapping(
      value = "/refresh",
      consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  public LoginResponseDTO refreshRequestDTO(RefreshRequestDTO request) throws InvalidAuthException {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("refresh_token", request.refreshToken());
    body.add("grant_type", "refresh_token");

    return getToken(body);
  }

  private LoginResponseDTO getToken(MultiValueMap<String, String> body) {
    var response =
        keycloakClient
            .post()
            .uri("/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(body)
            .retrieve()
            .onStatus(responseErrorHandler);

    JsonNode json = response.body(JsonNode.class);
    String accessToken = json.get("access_token").asText();
    String refreshToken = json.get("refresh_token").asText();

    return new LoginResponseDTO(accessToken, refreshToken);
  }

  @GetMapping("/getUser")
  public String getUser(HttpServletRequest request) {
    return request.getUserPrincipal().getName();
  }

  @PostMapping("/logout")
  public void logout(RefreshRequestDTO request) throws Exception {
    MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
    body.add("client_id", clientId);
    body.add("client_secret", clientSecret);
    body.add("refresh_token", request.refreshToken());

    keycloakClient
        .post()
        .uri("/protocol/openid-connect/logout")
        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        .body(body)
        .retrieve()
        .onStatus(responseErrorHandler);
  }

  private final ResponseErrorHandler responseErrorHandler =
      new ResponseErrorHandler() {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
          return false;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
          logger.warn(
              "Error when logging out: {} - {}",
              response.getStatusCode(),
              response.getStatusText());
        }
      };
}
