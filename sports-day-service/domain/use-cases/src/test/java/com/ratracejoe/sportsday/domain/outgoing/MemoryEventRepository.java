package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.outgoing.IEventRepository;

public class MemoryEventRepository extends MemoryGenericRepository<Event>
    implements IEventRepository {

  public MemoryEventRepository() {
    super(Event.class, Event::id);
  }
}
