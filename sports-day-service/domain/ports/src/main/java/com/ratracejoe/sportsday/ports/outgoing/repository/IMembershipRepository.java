package com.ratracejoe.sportsday.ports.outgoing.repository;

import java.util.List;
import java.util.UUID;

public interface IMembershipRepository {
  void addMembership(UUID teamId, UUID competitorId);

  List<UUID> getMemberIds(UUID teamId);
}
