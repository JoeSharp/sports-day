package com.ratracejoe.sportsday.ports.incoming.service.score;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import java.util.UUID;

public interface IFinishingOrderService {
  FinishingOrder createNew(UUID eventId);

  FinishingOrder getFinishingOrder(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException;

  void passFinishLine(UUID eventId, UUID participantId) throws IncorrectEventTypeException;
}
