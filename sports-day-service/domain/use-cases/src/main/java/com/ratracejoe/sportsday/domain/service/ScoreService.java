package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.domain.model.score.PointScoreSheet;
import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import java.util.UUID;

public class ScoreService implements IScoreService {
  private final FinishingOrderService finishingOrderService;
  private final TimedFinishingOrderService timedFinishingOrderService;
  private final PointScoreService pointScoreService;

  public ScoreService(
      FinishingOrderService finishingOrderService,
      TimedFinishingOrderService timedFinishingOrderService,
      PointScoreService pointScoreService) {
    this.finishingOrderService = finishingOrderService;
    this.timedFinishingOrderService = timedFinishingOrderService;
    this.pointScoreService = pointScoreService;
  }

  @Override
  public FinishingOrder getFinishingOrder(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException {
    return finishingOrderService.getFinishingOrder(eventId);
  }

  @Override
  public void passFinishLine(UUID eventId, UUID partipantId) throws IncorrectEventTypeException {
    finishingOrderService.passFinishLine(eventId, partipantId);
  }

  @Override
  public TimedFinishingOrder getTimedFinishingOrder(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException {
    return timedFinishingOrderService.getTimedFinishingOrder(eventId);
  }

  @Override
  public void passFinishLineInTime(UUID eventId, UUID participantId, long timeMilliseconds)
      throws IncorrectEventTypeException {
    timedFinishingOrderService.passFinishLineInTime(eventId, participantId, timeMilliseconds);
  }

  @Override
  public PointScoreSheet getPoints(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException {
    return pointScoreService.getPoints(eventId);
  }

  @Override
  public void addPoints(UUID eventId, UUID participantId, int points)
      throws IncorrectEventTypeException {
    pointScoreService.addPoints(eventId, participantId, points);
  }
}
