package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.facade.ActivityFacade;
import com.ratracejoe.sportsday.ports.incoming.IActivityFacade;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.IAuditLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainFacadeConfig {
  @Bean
  public IActivityFacade activityFacade(IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityFacade(repository, auditLogger);
  }
}
