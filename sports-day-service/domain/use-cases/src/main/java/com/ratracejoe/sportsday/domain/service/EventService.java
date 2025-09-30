package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.NoParticipantsException;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import com.ratracejoe.sportsday.domain.model.*;
import com.ratracejoe.sportsday.ports.incoming.service.IEventService;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IParticipantRepository;
import java.util.List;
import java.util.UUID;

public class EventService implements IEventService {
  private final IEventRepository eventRepository;
  private final IActivityRepository activityRepository;
  private final IParticipantRepository participantRepository;
  private final ICompetitorRepository competitorRepository;
  private final IScoreService scoreService;

  public EventService(
      IEventRepository eventRepository,
      IActivityRepository activityRepository,
      IParticipantRepository participantRepository,
      ICompetitorRepository competitorRepository,
      IScoreService scoreService)
      throws NotFoundException {
    this.eventRepository = eventRepository;
    this.activityRepository = activityRepository;
    this.participantRepository = participantRepository;
    this.competitorRepository = competitorRepository;
    this.scoreService = scoreService;
  }

  @Override
  public Event createEvent(
      UUID activityId, CompetitorType competitorType, ScoreType scoreType, int maxParticipants)
      throws NotFoundException {
    activityRepository.checkExists(activityId); // Ensure it exists
    Event event =
        new Event(
            UUID.randomUUID(),
            activityId,
            EventState.CREATING,
            competitorType,
            scoreType,
            maxParticipants);
    eventRepository.save(event);
    scoreService.createNew(event.id(), scoreType);
    return event;
  }

  @Override
  public Event getById(UUID id) throws NotFoundException {
    return eventRepository.getById(id);
  }

  @Override
  public List<Event> getAll() {
    return eventRepository.getAll();
  }

  @Override
  public void registerParticipant(UUID eventId, UUID participantId) throws NotFoundException {
    eventRepository.checkExists(eventId);
    competitorRepository.checkExists(participantId);
    participantRepository.addParticipant(eventId, participantId);
  }

  @Override
  public List<Competitor> getParticipants(UUID eventId) throws NotFoundException {
    return participantRepository.getParticipants(eventId).stream()
        .map(competitorRepository::getById)
        .toList();
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

  @Override
  public void deleteById(UUID id) throws NotFoundException, UnauthorisedException {
    eventRepository.deleteById(id);
  }
}
