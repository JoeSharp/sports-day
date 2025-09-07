package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.ports.incoming.IActivityFacade;
import com.ratracejoe.sportsday.rest.controller.ActivityController;
import com.ratracejoe.sportsday.rest.controller.AuthController;
import com.ratracejoe.sportsday.rest.exception.SportsExceptionHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestAdapterConfig {

  @Bean
  public ActivityController activityController(IActivityFacade activityFacade) {
    return new ActivityController(activityFacade);
  }

  @Bean
  public AuthController authController(
      @Qualifier("keycloak") RestClient keycloakClient,
      @Value("${spring.security.oauth2.client.registration.keycloak.clientId}") String clientId,
      @Value("${spring.security.oauth2.client.registration.keycloak.clientSecret}")
          String clientSecret) {
    return new AuthController(keycloakClient, clientId, clientSecret);
  }

  @Bean
  public SportsExceptionHandler exceptionHandler() {
    return new SportsExceptionHandler();
  }
}
