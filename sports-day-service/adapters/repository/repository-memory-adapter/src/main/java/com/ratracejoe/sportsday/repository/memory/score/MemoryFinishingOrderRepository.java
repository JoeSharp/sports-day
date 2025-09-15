package com.ratracejoe.sportsday.repository.memory.score;

import com.ratracejoe.sportsday.domain.model.score.FinishingOrder;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryGenericRepository;

public class MemoryFinishingOrderRepository extends MemoryGenericRepository<FinishingOrder>
    implements IFinishingOrderRepository {
  public MemoryFinishingOrderRepository() {
    super(FinishingOrder.class, FinishingOrder::eventId);
  }
}
