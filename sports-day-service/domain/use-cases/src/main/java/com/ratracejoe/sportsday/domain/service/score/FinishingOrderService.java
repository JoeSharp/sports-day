package com.ratracejoe.sportsday.domain.service.score;

import com.ratracejoe.sportsday.domain.exception.IncorrectEventTypeException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import java.util.UUID;

public class FinishingOrderService implements IFinishingOrderService {
  private final ICompetitorRepository competitorRepository;
  private final IFinishingOrderRepository finishingOrderRepository;

  public FinishingOrderService(
      ICompetitorRepository competitorRepository,
      IFinishingOrderRepository finishingOrderRepository) {
    this.competitorRepository = competitorRepository;
    this.finishingOrderRepository = finishingOrderRepository;
  }

  @Override
  public FinishingOrder createNew(UUID eventId) {
    FinishingOrder finishingOrder = FinishingOrder.create(eventId);
    finishingOrderRepository.save(finishingOrder);
    return finishingOrder;
  }

  @Override
  public FinishingOrder getFinishingOrder(UUID eventId)
      throws NotFoundException, IncorrectEventTypeException {
    return finishingOrderRepository.getById(eventId);
  }

  @Override
  public void passFinishLine(UUID eventId, UUID partipantId) throws IncorrectEventTypeException {
    FinishingOrder finishingOrder = finishingOrderRepository.getById(eventId);
    competitorRepository.checkExists(partipantId);
    FinishingOrder newFinishingOrder = finishingOrder.withFinisher(partipantId);
    finishingOrderRepository.save(newFinishingOrder);
  }
}
