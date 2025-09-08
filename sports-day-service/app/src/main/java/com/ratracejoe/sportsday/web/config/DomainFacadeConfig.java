package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.facade.ActivityFacade;
import com.ratracejoe.sportsday.domain.facade.TeamFacade;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.incoming.IActivityFacade;
import com.ratracejoe.sportsday.ports.incoming.ITeamFacade;
import com.ratracejoe.sportsday.ports.outgoing.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import com.ratracejoe.sportsday.ports.outgoing.IMembershipRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainFacadeConfig {
  @Bean
  public IActivityFacade activityFacade(
      IGenericRepository<Activity> repository, IAuditLogger auditLogger) {
    return new ActivityFacade(repository, auditLogger);
  }

  @Bean
  public ITeamFacade teamFacade(
      IGenericRepository<Team> teamRepo,
      IGenericRepository<Competitor> competitorRepo,
      IMembershipRepository membershipRepo,
      IAuditLogger auditLogger) {
    return new TeamFacade(auditLogger, teamRepo, competitorRepo, membershipRepo);
  }
}
