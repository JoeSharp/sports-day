package com.ratracejoe.sportsday.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.ratracejoe.sportsday.exception.InvalidAuthException;
import com.ratracejoe.sportsday.model.rest.LoginRequestDTO;
import com.ratracejoe.sportsday.model.rest.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
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
  public LoginResponseDTO login(LoginRequestDTO loginRequest) throws InvalidAuthException {

    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id", clientId);
    map.add("client_secret", clientSecret);
    map.add("username", loginRequest.username());
    map.add("password", loginRequest.password());
    map.add("grant_type", "password");

    var response =
        keycloakClient
            .post()
            .uri("/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(map)
            .retrieve();

    JsonNode json = response.body(JsonNode.class);
    String accessToken = json.get("access_token").asText();
    String refreshToken = json.get("refresh_token").asText();

    return new LoginResponseDTO(accessToken, refreshToken);
  }

  @GetMapping("/getUser")
  public String getUser(HttpServletRequest request) {
    return request.getUserPrincipal().getName();
  }

  @GetMapping("/logout")
  public void logout(HttpServletRequest request) throws Exception {
    request.logout();
  }
}
