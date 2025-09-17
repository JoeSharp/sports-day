package com.ratracejoe.sportsday.rest.controller;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.rest.model.EventDTO;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/events", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class EventController {
  private static final Logger LOGGER = LoggerFactory.getLogger(EventController.class);
  private final IEventService eventService;

  @GetMapping("/{id}")
  public EventDTO getById(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Retrieving Event by {}", id);
    Event event = eventService.getById(id);
    return EventDTO.fromDomain(event);
  }
}
