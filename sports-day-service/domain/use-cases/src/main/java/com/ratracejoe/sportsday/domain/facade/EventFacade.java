package com.ratracejoe.sportsday.domain.facade;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
import com.ratracejoe.sportsday.ports.incoming.IEventFacade;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.IEventRepository;
import java.util.UUID;

public class EventFacade implements IEventFacade {
  private final IEventRepository eventRepository;
  private final IActivityRepository activityRepository;

  public EventFacade(IEventRepository eventRepository, IActivityRepository activityRepository)
      throws NotFoundException {
    this.eventRepository = eventRepository;
    this.activityRepository = activityRepository;
  }

  @Override
  public Event createEvent(UUID activityId, ParticipantType participantType, int maxParticipants)
      throws NotFoundException {
    Activity activity = activityRepository.getById(activityId);
    Event event = new Event(UUID.randomUUID(), activity, participantType, maxParticipants);
    eventRepository.save(event);
    return event;
  }

  @Override
  public Event getById(UUID id) throws NotFoundException {
    return null;
  }

  @Override
  public void registerParticipant(UUID eventId, UUID participantId) throws NotFoundException {}

  @Override
  public void startEvent(UUID id) throws NotFoundException {}

  @Override
  public void stopEvent(UUID id) throws NotFoundException {}
}
