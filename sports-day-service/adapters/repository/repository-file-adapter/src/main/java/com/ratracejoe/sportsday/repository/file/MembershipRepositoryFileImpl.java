package com.ratracejoe.sportsday.repository.file;

import com.ratracejoe.sportsday.ports.outgoing.repository.IMembershipRepository;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class MembershipRepositoryFileImpl extends GenericRelationshipFileRepository
    implements IMembershipRepository {

  public MembershipRepositoryFileImpl(Path rootDirectory) {
    super("membership", rootDirectory);
  }

  @Override
  public void addMembership(UUID teamId, UUID competitorId) {
    relate(teamId, competitorId);
  }

  @Override
  public List<UUID> getMemberIds(UUID teamId) {
    return getRightByLeft(teamId);
  }
}
