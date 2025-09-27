package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import com.ratracejoe.sportsday.rest.auth.controller.AuthController;
import com.ratracejoe.sportsday.rest.controller.ActivityController;
import com.ratracejoe.sportsday.rest.controller.CompetitorController;
import com.ratracejoe.sportsday.rest.controller.EventController;
import com.ratracejoe.sportsday.rest.controller.TeamController;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestAdapterConfig {

  @Bean
  public CompetitorController competitorController(ICompetitorService competitorService) {
    return new CompetitorController(competitorService);
  }

  @Bean
  public ActivityController activityController(IActivityService activityService) {
    return new ActivityController(activityService);
  }

  @Bean
  public EventController eventController(
      IEventService eventService, ICompetitorService competitorService) {
    return new EventController(eventService, competitorService);
  }

  @Bean
  public TeamController teamController(ITeamService teamService) {
    return new TeamController(teamService);
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
