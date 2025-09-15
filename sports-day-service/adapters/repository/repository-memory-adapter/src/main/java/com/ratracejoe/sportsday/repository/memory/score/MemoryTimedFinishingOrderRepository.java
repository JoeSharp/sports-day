package com.ratracejoe.sportsday.repository.memory.score;

import com.ratracejoe.sportsday.domain.model.score.TimedFinishingOrder;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryGenericRepository;

public class MemoryTimedFinishingOrderRepository
    extends MemoryGenericRepository<TimedFinishingOrder> implements ITimedFinishingOrderRepository {
  public MemoryTimedFinishingOrderRepository() {
    super(TimedFinishingOrder.class, TimedFinishingOrder::eventId);
  }
}
