package com.ratracejoe.sportsday.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;

public class KeycloakExtension implements BeforeAllCallback, AfterAllCallback {
  private static final String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:26.1.3";
  private static final String CLIENT_ID = "timesheets-service";
  private static final String CLIENT_SECRET = "rX0uyWb89PxdeclkQoLMtmRtCLRxFlKy";

  public static int KEYCLOAK_PORT = 8080;
  private static final GenericContainer<?> keycloak =
      new GenericContainer<>(KEYCLOAK_IMAGE)
          .withCommand("start-dev --import-realm")
          .withExposedPorts(KEYCLOAK_PORT)
          .withClasspathResourceMapping(
              "keycloak/", "/opt/keycloak/data/import/", BindMode.READ_ONLY);
  private static RestClient keycloakClient;

  public HttpHeaders getAuthHeaders() {
    HttpHeaders headers = new HttpHeaders();
    headers.setBearerAuth(login());
    return headers;
  }

  public String login() {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    map.add("client_id", CLIENT_ID);
    map.add("client_secret", CLIENT_SECRET);
    map.add("username", TestUser.JOE.getUsername());
    map.add("password", TestUser.JOE.getPassword());
    map.add("grant_type", "password");

    JsonNode response =
        keycloakClient
            .post()
            .uri("/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(map)
            .retrieve()
            .body(JsonNode.class);
    return response.get("access_token").asText();
  }

  public void beforeAll() {
    keycloak.start();
    keycloakClient = RestClient.builder().baseUrl(getIssuerUri()).build();
  }

  @Override
  public void beforeAll(ExtensionContext context) {
    beforeAll();
  }

  public void afterAll() {
    keycloak.stop();
    keycloak.close();
  }

  @Override
  public void afterAll(ExtensionContext context) {
    afterAll();
  }

  public String getIssuerUri() {
    return String.format(
        "http://localhost:%d/realms/ratracejoe", keycloak.getMappedPort(KEYCLOAK_PORT));
  }
}
