package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.domain.exception.InvalidEventStateException;
import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.GoalType;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
import java.util.List;
import java.util.UUID;

public interface IEventService {
  Event createEvent(
      UUID activityId, ParticipantType participantType, GoalType goalType, int maxParticipants)
      throws NotFoundException;

  Event getById(UUID id) throws NotFoundException;

  void registerParticipant(UUID eventId, UUID participantId)
      throws NotFoundException, InvalidEventStateException;

  List<Competitor> getParticipants(UUID eventId) throws NotFoundException;

  void startEvent(UUID id)
      throws NotFoundException, InvalidEventStateException, NoParticipantsException;

  void stopEvent(UUID id) throws NotFoundException, InvalidEventStateException;
}
