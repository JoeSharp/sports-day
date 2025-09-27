package com.ratracejoe.sportsday.repository.file;

import com.ratracejoe.sportsday.ports.outgoing.repository.IParticipantRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class ParticipantRepositoryFileImpl extends GenericRelationshipFileRepository
    implements IParticipantRepository {
  public ParticipantRepositoryFileImpl(Path rootDirectory) {
    super("participation", rootDirectory);
  }

  @Override
  public void addParticipant(UUID eventId, UUID participantId) {
    relate(eventId, participantId);
  }

  @Override
  public List<UUID> getParticipants(UUID eventId) {
    return getRightByLeft(eventId);
  }
}
