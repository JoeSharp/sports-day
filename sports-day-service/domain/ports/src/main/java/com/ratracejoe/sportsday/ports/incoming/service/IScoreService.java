package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import java.util.UUID;

public interface IScoreService {
  default void addPoint(UUID eventId, UUID participantId) throws IncorrectEventTypeException {
    addPoints(eventId, participantId, 1);
  }

  void addPoints(UUID eventId, UUID participantId, int points) throws IncorrectEventTypeException;

  void passFinishLine(UUID eventId, UUID partipantId) throws IncorrectEventTypeException;

  void registerDistance(UUID eventId, UUID participantId, int distance)
      throws IncorrectEventTypeException;
}
