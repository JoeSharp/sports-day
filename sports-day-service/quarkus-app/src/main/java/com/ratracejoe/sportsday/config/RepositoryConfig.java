package com.ratracejoe.sportsday.config;

import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ICompetitorRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IEventRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IMembershipRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.IParticipantRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.ITeamRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IFinishingOrderRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.IPointScoreSheetRepository;
import com.ratracejoe.sportsday.ports.outgoing.repository.score.ITimedFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryActivityRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryCompetitorRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryEventRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryMembershipRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryParticipantRepository;
import com.ratracejoe.sportsday.repository.memory.MemoryTeamRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryFinishingOrderRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryPointScoreSheetRepository;
import com.ratracejoe.sportsday.repository.memory.score.MemoryTimedFinishingOrderRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class RepositoryConfig {

  @Produces
  public IActivityRepository activityRepository() {
    return new MemoryActivityRepository();
  }

  @Produces
  public ICompetitorRepository competitorRepository() {
    return new MemoryCompetitorRepository();
  }

  @Produces
  public ITeamRepository teamRepository() {
    return new MemoryTeamRepository();
  }

  @Produces
  public IMembershipRepository membershipRepository(
      ITeamRepository teamRepository, ICompetitorRepository competitorRepository) {
    return new MemoryMembershipRepository(teamRepository, competitorRepository);
  }

  @Produces
  public IParticipantRepository participantRepository(
      IEventRepository eventRepository, ICompetitorRepository competitorRepository) {
    return new MemoryParticipantRepository(eventRepository, competitorRepository);
  }

  @Produces
  public IEventRepository eventRepository() {
    return new MemoryEventRepository();
  }

  @Produces
  public ITimedFinishingOrderRepository timedFinishingOrderRepository() {
    return new MemoryTimedFinishingOrderRepository();
  }

  @Produces
  public IFinishingOrderRepository finishingOrderRepository() {
    return new MemoryFinishingOrderRepository();
  }

  @Produces
  public IPointScoreSheetRepository scoreSheetRepository() {
    return new MemoryPointScoreSheetRepository();
  }
}
