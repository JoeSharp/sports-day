package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
import com.ratracejoe.sportsday.ports.incoming.IEventFacade;
import java.util.UUID;

public class EventFacade implements IEventFacade {
  @Override
  public void createEvent(UUID activityId, ParticipantType participantType, int maxParticipants) {}

  @Override
  public Event getById(UUID id) {
    return null;
  }

  @Override
  public void registerParticipant(UUID eventId, UUID participantId) {}

  @Override
  public void startEvent(UUID id) {}

  @Override
  public void stopEvent(UUID id) {}
}
