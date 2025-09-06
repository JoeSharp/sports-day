package com.ratracejoe.sportsday.incoming;

import com.ratracejoe.sportsday.domain.exception.CompetitorNotFoundException;
import com.ratracejoe.sportsday.domain.exception.TeamNotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import java.util.List;
import java.util.UUID;

public interface ITeamFacade {
  Team createTeam(String name);

  Team getById(UUID id) throws TeamNotFoundException;

  List<Competitor> getMembers(UUID id) throws TeamNotFoundException;

  void registerMember(UUID teamId, UUID competitorId)
      throws TeamNotFoundException, CompetitorNotFoundException;
}
