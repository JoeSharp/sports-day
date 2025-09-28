package com.ratracejoe.sportsday.command.services;

import static com.ratracejoe.sportsday.command.InvalidCommandException.parse;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.ScoreType;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import java.util.List;
import java.util.UUID;

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

  private void createEvent(String input) throws InvalidCommandException {
    List<String> parts = Command.splitRespectingQuotes(input);
    UUID activityId = UUID.fromString(parts.get(0));
    CompetitorType competitorType = parse(parts.get(1), CompetitorType::valueOf);
    ScoreType scoreType = parse(parts.get(2), ScoreType::valueOf);
    int maxParticipants = parse(parts.get(3), Integer::parseInt);

    eventService.createEvent(activityId, competitorType, scoreType, maxParticipants);
  }

  @Override
  String domainToLogString(Event domain) {
    return String.format("Event id: %s", domain.id());
  }
}
