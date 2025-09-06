package com.ratracejoe.sportsday.outgoing;

import com.ratracejoe.sportsday.domain.exception.CompetitorNotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;

public interface ICompetitorRepository
    extends IGenericRepository<Competitor, CompetitorNotFoundException> {}
