package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IParticipantRepository;
import java.util.List;
import java.util.UUID;

public class MemoryParticipantRepository implements IParticipantRepository {

  private final MemoryRelationshipRepository<Event, Competitor> relationshipRepository;

  public MemoryParticipantRepository(
      IEventRepository eventRepository, ICompetitorRepository competitorRepository) {
    this.relationshipRepository =
        new MemoryRelationshipRepository<>(eventRepository, competitorRepository);
  }

  @Override
  public void addParticipant(UUID eventId, UUID competitorId) {
    relationshipRepository.addRelationship(eventId, competitorId);
  }

  @Override
  public List<UUID> getParticipants(UUID eventId) {
    return relationshipRepository.getRightsForLeft(eventId);
  }
}
