package com.ratracejoe.sportsday.ports.incoming;

import com.ratracejoe.sportsday.domain.exception.InvalidEventStateException;
import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
import java.util.UUID;

public interface IEventFacade {
  Event createEvent(UUID activityId, ParticipantType participantType, int maxParticipants)
      throws NotFoundException;

  Event getById(UUID id) throws NotFoundException;

  void registerParticipant(UUID eventId, UUID participantId)
      throws NotFoundException, InvalidEventStateException;

  void startEvent(UUID id)
      throws NotFoundException, InvalidEventStateException, NoParticipantsException;

  void stopEvent(UUID id) throws NotFoundException, InvalidEventStateException;
}
