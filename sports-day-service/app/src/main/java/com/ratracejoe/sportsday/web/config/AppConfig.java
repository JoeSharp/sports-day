package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.service.ActivityFacade;
import com.ratracejoe.sportsday.incoming.IActivityFacade;
import com.ratracejoe.sportsday.jpa.repository.ActivityJpaRepository;
import com.ratracejoe.sportsday.jpa.repository.ActivityRedisCache;
import com.ratracejoe.sportsday.jpa.service.ActivityRepositoryImpl;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.outgoing.IAuditLogger;
import com.ratracejoe.sportsday.rest.controller.ActivityController;
import com.ratracejoe.sportsday.rest.controller.AuthController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class AppConfig {
  @Bean
  public IActivityFacade activityFacade(IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityFacade(repository, auditLogger);
  }

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
  public IActivityRepository activityRepository(
      ActivityJpaRepository repository, ActivityRedisCache cache) {
    return new ActivityRepositoryImpl(repository, cache);
  }
}
