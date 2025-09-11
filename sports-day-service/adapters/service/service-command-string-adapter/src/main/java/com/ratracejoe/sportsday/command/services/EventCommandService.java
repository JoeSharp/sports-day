package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;

public class EventCommandService implements ICommandHandler {
  private final IEventService eventService;

  public EventCommandService(final IEventService eventService) {
    this.eventService = eventService;
  }

    @Override
    public void handleCommand() throws InvalidCommandException {

    }
}
