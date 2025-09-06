package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.domain.service.ActivityFacade;
import com.ratracejoe.sportsday.incoming.IActivityFacade;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.outgoing.IAuditLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  @Bean
  public IActivityFacade activityFacade(IActivityRepository repository, IAuditLogger auditLogger) {
    return new ActivityFacade(repository, auditLogger);
  }
}
