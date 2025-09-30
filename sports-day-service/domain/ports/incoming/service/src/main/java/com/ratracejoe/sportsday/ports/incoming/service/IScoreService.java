package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.domain.model.ScoreType;
import java.util.UUID;

public interface IScoreService {
  void createNew(UUID eventId, ScoreType scoreType);
}
