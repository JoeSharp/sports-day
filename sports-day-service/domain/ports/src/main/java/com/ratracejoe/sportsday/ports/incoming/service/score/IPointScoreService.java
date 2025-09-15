package com.ratracejoe.sportsday.ports.incoming.service.score;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import java.util.UUID;

public interface IPointScoreService {
  PointScoreSheet getPoints(UUID eventId) throws NotFoundException, IncorrectEventTypeException;

  default void addPoint(UUID eventId, UUID participantId) throws IncorrectEventTypeException {
    addPoints(eventId, participantId, 1);
  }

  void addPoints(UUID eventId, UUID participantId, int points) throws IncorrectEventTypeException;
}
