package com.ratracejoe.sportsday.repository.cache;

import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;

public class EventCachedRepository extends CachedRepository<Event, IEventRepository>
    implements IEventRepository {
  public EventCachedRepository(IEventRepository persistent, IEventRepository cached) {
    super(persistent, cached);
  }
}
