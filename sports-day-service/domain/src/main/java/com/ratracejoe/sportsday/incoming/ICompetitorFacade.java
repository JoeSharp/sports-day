package com.ratracejoe.sportsday.incoming;

import com.ratracejoe.sportsday.domain.exception.CompetitorNotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import java.util.UUID;

public interface ICompetitorFacade {
  Competitor createCompetitor(String name);

  Competitor getById(UUID id) throws CompetitorNotFoundException;
}
