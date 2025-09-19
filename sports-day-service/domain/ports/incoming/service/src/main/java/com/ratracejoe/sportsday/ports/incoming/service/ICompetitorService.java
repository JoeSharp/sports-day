package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.CompetitorType;
import java.util.UUID;

public interface ICompetitorService {
  default Competitor createCompetitor(String name) {
    return createCompetitor(CompetitorType.INDIVIDUAL, name);
  }

  Competitor createCompetitor(CompetitorType type, String name) throws UnauthorisedException;

  Competitor getById(UUID id) throws NotFoundException;
}
