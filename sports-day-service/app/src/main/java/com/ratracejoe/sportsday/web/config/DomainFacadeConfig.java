package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.facade.ActivityFacade;
import com.ratracejoe.sportsday.domain.facade.CompetitorFacade;
import com.ratracejoe.sportsday.domain.facade.TeamFacade;
import com.ratracejoe.sportsday.ports.incoming.IActivityFacade;
import com.ratracejoe.sportsday.ports.incoming.ICompetitorFacade;
import com.ratracejoe.sportsday.ports.incoming.ITeamFacade;
import com.ratracejoe.sportsday.ports.outgoing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainFacadeConfig {
  @Bean
  public IActivityFacade activityFacade(IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityFacade(repository, auditLogger);
  }

  @Bean
  public ITeamFacade teamFacade(
      ITeamRepository teamRepo,
      ICompetitorRepository competitorRepo,
      IMembershipRepository membershipRepo,
      IAuditLogger auditLogger) {
    return new TeamFacade(auditLogger, teamRepo, competitorRepo, membershipRepo);
  }

  @Bean
  public ICompetitorFacade competitorFacade(ICompetitorRepository competitorRepository) {
    return new CompetitorFacade(competitorRepository);
  }
}
