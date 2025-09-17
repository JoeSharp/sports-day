package com.ratracejoe.sportsday.repository.jpa.service;

import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.EventEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class EventRepositoryJpaImpl extends GenericRepositoryJpaImpl<Event, EventEntity>
    implements IEventRepository {
  public EventRepositoryJpaImpl(JpaRepository<EventEntity, UUID> repository) {
    super(repository, EventEntity::domainToEntity, EventEntity::entityToDomain);
  }
}
