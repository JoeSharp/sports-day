package com.ratracejoe.sportsday.domain.repository;

import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.outgoing.IEventRepository;

public class EventCachedRepository extends CachedRepository<Event, IEventRepository>
    implements IEventRepository {
  public EventCachedRepository(IEventRepository persistent, IEventRepository cached) {
    super(persistent, cached);
  }
}
