package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;

public class MemoryEventRepository extends MemoryGenericRepository<Event>
    implements IEventRepository {

  public MemoryEventRepository() {
    super(Event.class, Event::id);
  }
}
