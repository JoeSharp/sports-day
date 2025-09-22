package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import java.util.List;
import java.util.UUID;

public interface ITeamService {
  Team createTeam(String name) throws UnauthorisedException;

  Team getById(UUID id) throws NotFoundException;

  List<Team> getAll();

  List<Competitor> getMembers(UUID id) throws NotFoundException;

  void registerMember(UUID teamId, UUID competitorId)
      throws NotFoundException, UnauthorisedException;

  void deleteByUuid(UUID id) throws NotFoundException, UnauthorisedException;
}
