package com.ratracejoe.sportsday.rest.controller;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.incoming.service.ICompetitorService;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.rest.model.CompetitorDTO;
import com.ratracejoe.sportsday.rest.model.EventDTO;
import com.ratracejoe.sportsday.rest.model.NewEventDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {
  private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
  private final IEventService eventService;
  private final ICompetitorService competitorService;

  @GetMapping("/{id}")
  public EventDTO getById(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Retrieving Event by {}", id);
    Event event = eventService.getById(id);
    return EventDTO.fromDomain(event);
  }

  @GetMapping("/{id}/participants")
  public List<CompetitorDTO> getCompetitors(@PathVariable UUID id) throws NotFoundException {
    return eventService.getParticipants(id).stream().map(CompetitorDTO::fromDomain).toList();
  }

  @GetMapping
  public List<EventDTO> getAll() {
    return eventService.getAll().stream().map(EventDTO::fromDomain).toList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public EventDTO createEvent(@RequestBody NewEventDTO newEvent) {
    var event =
        eventService.createEvent(
            newEvent.activityId(),
            newEvent.competitorType(),
            newEvent.scoreType(),
            newEvent.maxParticipants());
    LOGGER.info("Created Event {}", event);
    return EventDTO.fromDomain(event);
  }

  @PostMapping("/{eventId}/registerParticipant/{participantId}")
  public EventDTO registerParticipant(
      @PathVariable UUID eventId, @PathVariable UUID participantId) {
    eventService.registerParticipant(eventId, participantId);
    return getById(eventId);
  }

  @PostMapping("/{eventId}/startEvent")
  public EventDTO startEvent(@PathVariable UUID eventId) {
    eventService.startEvent(eventId);
    return getById(eventId);
  }

  @PostMapping("/{eventId}/stopEvent")
  public EventDTO stopEvent(@PathVariable UUID eventId) {
    eventService.stopEvent(eventId);
    return getById(eventId);
  }

  @DeleteMapping("/{id}")
  public void deleteEvent(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Deleting event {}", id);
    eventService.deleteById(id);
  }
}
