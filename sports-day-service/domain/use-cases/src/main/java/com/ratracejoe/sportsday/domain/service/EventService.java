package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Event;
import com.ratracejoe.sportsday.domain.model.EventState;
import com.ratracejoe.sportsday.domain.model.ParticipantType;
import com.ratracejoe.sportsday.ports.incoming.IEventService;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.IEventRepository;
import com.ratracejoe.sportsday.ports.outgoing.IParticipantRepository;
import java.util.UUID;

public class EventService implements IEventService {
  private final IEventRepository eventRepository;
  private final IActivityRepository activityRepository;
  private final IParticipantRepository participantRepository;
  private final ICompetitorRepository competitorRepository;

  public EventService(
      IEventRepository eventRepository,
      IActivityRepository activityRepository,
      IParticipantRepository participantRepository,
      ICompetitorRepository competitorRepository)
      throws NotFoundException {
    this.eventRepository = eventRepository;
    this.activityRepository = activityRepository;
    this.participantRepository = participantRepository;
    this.competitorRepository = competitorRepository;
  }

  @Override
  public Event createEvent(UUID activityId, ParticipantType participantType, int maxParticipants)
      throws NotFoundException {
    activityRepository.checkExists(activityId); // Ensure it exists
    Event event =
        new Event(
            UUID.randomUUID(), activityId, EventState.CREATING, participantType, maxParticipants);
    eventRepository.save(event);
    return event;
  }

  @Override
  public Event getById(UUID id) throws NotFoundException {
    return eventRepository.getById(id);
  }

  @Override
  public void registerParticipant(UUID eventId, UUID participantId) throws NotFoundException {
    eventRepository.checkExists(eventId);
    competitorRepository.checkExists(participantId);
    participantRepository.addParticipant(eventId, participantId);
  }

  @Override
  public void startEvent(UUID id) throws NotFoundException {
    if (participantRepository.getParticipants(id).isEmpty()) {
      throw new NoParticipantsException();
    }
    Event updated = eventRepository.getById(id).withState(EventState.STARTED);
    eventRepository.save(updated);
  }

  @Override
  public void stopEvent(UUID id) throws NotFoundException {
    Event updated = eventRepository.getById(id).withState(EventState.FINISHED);
    eventRepository.save(updated);
  }
}
