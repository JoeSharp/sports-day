package com.ratracejoe.sportsday.ports.outgoing.audit;

public interface IAuditLogger {
  void sendAudit(String msg);
}
