package com.ratracejoe.sportsday.ports.incoming.service.score;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import java.util.UUID;

public interface ITimedFinishingOrderService {
  TimedFinishingOrder getTimedFinishingOrder(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException;

  void passFinishLineInTime(UUID eventId, UUID participantId, long timeMilliseconds)
      throws IncorrectEventTypeException;
}
