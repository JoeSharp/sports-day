package com.ratracejoe.sportsday.audit;

import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class QuarkusKafkaAuditLogger implements IAuditLogger {

  @Inject
  @Channel("audit")
  Emitter<String> emitter;

  @Override
  public void sendAudit(String msg) {
    emitter.send(msg);
  }
}
