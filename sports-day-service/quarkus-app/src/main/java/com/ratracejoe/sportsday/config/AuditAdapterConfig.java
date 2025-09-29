package com.ratracejoe.sportsday.config;

import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class AuditAdapterConfig {

  @Produces
  public IAuditLogger auditLogger() {
    return new MemoryAuditLogger();
  }
}
