package com.ratracejoe.sportsday.outgoing;

import com.ratracejoe.sportsday.domain.exception.TeamNotFoundException;
import com.ratracejoe.sportsday.domain.model.Team;

public interface ITeamRepository extends IGenericRepository<Team, TeamNotFoundException> {}
