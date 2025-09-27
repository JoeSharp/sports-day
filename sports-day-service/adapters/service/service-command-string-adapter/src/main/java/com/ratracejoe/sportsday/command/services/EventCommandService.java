package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;

public class EventCommandService extends GenericCommandService<Event> {
  private final IEventService eventService;

  public EventCommandService(
      final IEventService eventService, final IResponseListener responseListener) {
    super(responseListener);
    this.eventService = eventService;
    registerHandler("create", this::createEvent);
    registerHandler("getAll", getAllHandler(eventService::getAll));
    registerHandler("getById", getByIdHandler(eventService::getById));
    registerHandler("deleteById", deleteHandler(eventService::deleteById));
  }

  private void createEvent(String input) {}

  @Override
  String domainToLogString(Event domain) {
    return String.format("Event id: %s", domain.id());
  }
}
