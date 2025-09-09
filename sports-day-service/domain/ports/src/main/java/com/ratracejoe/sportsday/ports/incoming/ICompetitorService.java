package com.ratracejoe.sportsday.ports.incoming;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import java.util.UUID;

public interface ICompetitorService {
  Competitor createCompetitor(String name);

  Competitor getById(UUID id) throws NotFoundException;
}
