package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import java.util.UUID;

public class ScoreService implements IScoreService {
  @Override
  public void addPoints(UUID eventId, UUID participantId, int points)
      throws IncorrectEventTypeException {}

  @Override
  public void passFinishLine(UUID eventId, UUID partipantId) throws IncorrectEventTypeException {}

  @Override
  public void registerDistance(UUID eventId, UUID participantId, int distance)
      throws IncorrectEventTypeException {}
}
