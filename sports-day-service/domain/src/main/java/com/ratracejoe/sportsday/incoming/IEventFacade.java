package com.ratracejoe.sportsday.incoming;

import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
import java.util.UUID;

public interface IEventFacade {
  void createEvent(UUID activityId, ParticipantType participantType, int maxParticipants);

  Event getById(UUID id);

  void registerParticipant(UUID eventId, UUID participantId);

  void startEvent(UUID id);

  void stopEvent(UUID id);
}
