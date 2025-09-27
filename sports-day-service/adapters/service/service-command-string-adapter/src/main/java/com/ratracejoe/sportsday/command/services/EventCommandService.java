package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.ICommandHandler;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;

public class EventCommandService implements ICommandHandler {
  private final IEventService eventService;
  private final IResponseListener responseListener;

  public EventCommandService(
      final IEventService eventService, final IResponseListener responseListener) {
    this.eventService = eventService;
    this.responseListener = responseListener;
  }

  @Override
  public void handleCommand(String commandStr) throws InvalidCommandException {}
}
