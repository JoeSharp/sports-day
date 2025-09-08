package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.jpa.model.MemberId;
import com.ratracejoe.sportsday.jpa.model.MembershipEntity;
import com.ratracejoe.sportsday.jpa.repository.MembershipJpaRepository;
import com.ratracejoe.sportsday.ports.outgoing.IMembershipRepository;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MembershipRepositoryJpaImpl implements IMembershipRepository {
  private final MembershipJpaRepository repository;

  @Override
  public void addMembership(UUID teamId, UUID competitorId) {
    MembershipEntity entity = new MembershipEntity(new MemberId(teamId, competitorId));
    repository.save(entity);
  }

  @Override
  public List<UUID> getMemberIds(UUID teamId) {
    return Collections.emptyList();
  }
}
