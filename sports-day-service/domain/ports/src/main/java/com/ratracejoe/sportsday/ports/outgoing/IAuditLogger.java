package com.ratracejoe.sportsday.ports.outgoing;

public interface IAuditLogger {
  void sendAudit(String msg);
}
