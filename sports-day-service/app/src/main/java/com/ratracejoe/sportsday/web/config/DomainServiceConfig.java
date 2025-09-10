package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.service.ActivityService;
import com.ratracejoe.sportsday.domain.service.CompetitorService;
import com.ratracejoe.sportsday.domain.service.TeamService;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IMembershipRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ITeamRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainServiceConfig {
  @Bean
  public IActivityService activityService(
      IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityService(repository, auditLogger);
  }

  @Bean
  public ITeamService teamService(
      ITeamRepository teamRepo,
      ICompetitorRepository competitorRepo,
      IMembershipRepository membershipRepo,
      IAuditLogger auditLogger) {
    return new TeamService(auditLogger, teamRepo, competitorRepo, membershipRepo);
  }

  @Bean
  public ICompetitorService competitorService(ICompetitorRepository competitorRepository) {
    return new CompetitorService(competitorRepository);
  }
}
