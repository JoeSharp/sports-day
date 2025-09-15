package com.ratracejoe.sportsday.domain.service.score;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import java.util.UUID;

public class TimedFinishingOrderService implements ITimedFinishingOrderService {
  private final ICompetitorRepository competitorRepository;
  private final ITimedFinishingOrderRepository finishingOrderRepository;

  public TimedFinishingOrderService(
      ICompetitorRepository competitorRepository,
      ITimedFinishingOrderRepository finishingOrderRepository) {
    this.competitorRepository = competitorRepository;
    this.finishingOrderRepository = finishingOrderRepository;
  }

  @Override
  public TimedFinishingOrder createNew(UUID eventId) {
    TimedFinishingOrder timedFinishingOrder = TimedFinishingOrder.create(eventId);
    finishingOrderRepository.save(timedFinishingOrder);
    return timedFinishingOrder;
  }

  @Override
  public TimedFinishingOrder getTimedFinishingOrder(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException {
    return finishingOrderRepository.getById(eventId);
  }

  @Override
  public void passFinishLineInTime(UUID eventId, UUID participantId, long timeMilliseconds)
      throws IncorrectEventTypeException {
    TimedFinishingOrder finishingOrder = finishingOrderRepository.getById(eventId);
    competitorRepository.checkExists(participantId);
    TimedFinishingOrder newFinishingOrder =
        finishingOrder.withParticipant(participantId, timeMilliseconds);
    finishingOrderRepository.save(newFinishingOrder);
  }
}
