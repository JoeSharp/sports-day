package com.ratracejoe.sportsday.ports.outgoing.repository;

import java.util.List;
import java.util.UUID;

public interface IParticipantRepository {
  void addParticipant(UUID eventId, UUID competitorId);

  List<UUID> getParticipants(UUID eventId);
}
