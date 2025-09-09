package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.service.ActivityService;
import com.ratracejoe.sportsday.domain.service.CompetitorService;
import com.ratracejoe.sportsday.domain.service.TeamService;
import com.ratracejoe.sportsday.ports.incoming.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.ITeamService;
import com.ratracejoe.sportsday.ports.outgoing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainFacadeConfig {
  @Bean
  public IActivityService activityFacade(IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityService(repository, auditLogger);
  }

  @Bean
  public ITeamService teamFacade(
      ITeamRepository teamRepo,
      ICompetitorRepository competitorRepo,
      IMembershipRepository membershipRepo,
      IAuditLogger auditLogger) {
    return new TeamService(auditLogger, teamRepo, competitorRepo, membershipRepo);
  }

  @Bean
  public ICompetitorService competitorFacade(ICompetitorRepository competitorRepository) {
    return new CompetitorService(competitorRepository);
  }
}
