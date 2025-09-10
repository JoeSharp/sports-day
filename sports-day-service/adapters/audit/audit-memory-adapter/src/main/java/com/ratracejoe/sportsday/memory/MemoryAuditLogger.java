package com.ratracejoe.sportsday.memory;

import com.ratracejoe.sportsday.ports.outgoing.audit.IAuditLogger;
import java.util.ArrayList;
import java.util.List;

public class MemoryAuditLogger implements IAuditLogger {
  private final List<String> messages = new ArrayList<>();

  @Override
  public void sendAudit(String msg) {
    messages.add(msg);
  }

  public List<String> getMessages() {
    return messages.stream().toList();
  }
}
