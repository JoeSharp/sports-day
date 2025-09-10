package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.ports.outgoing.repository.IGenericRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MemoryRelationshipRepository<L, R> {
  record Relationship(UUID leftId, UUID rightId) {}

  private final List<Relationship> relationships;

  private final IGenericRepository<L> leftRepository;
  private final IGenericRepository<R> rightRepository;

  public MemoryRelationshipRepository(
      IGenericRepository<L> leftRepository, IGenericRepository<R> rightRepository) {
    this.leftRepository = leftRepository;
    this.rightRepository = rightRepository;
    this.relationships = new ArrayList<>();
  }

  public void addRelationship(UUID left, UUID right) {
    leftRepository.checkExists(left);
    rightRepository.checkExists(right);
    relationships.add(new Relationship(left, right));
  }

  public List<UUID> getRightsForLeft(UUID left) {
    return relationships.stream()
        .filter(m -> m.leftId().equals(left))
        .map(Relationship::rightId)
        .toList();
  }

  public List<UUID> getLeftsForRight(UUID right) {
    return relationships.stream()
        .filter(m -> m.rightId().equals(right))
        .map(Relationship::leftId)
        .toList();
  }
}
