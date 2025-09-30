package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.model.ScoreType;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;
import java.util.UUID;

public class ScoreService implements IScoreService {
  private final IFinishingOrderService finishingOrderService;
  private final ITimedFinishingOrderService timedFinishingOrderService;
  private final IPointScoreService pointScoreService;

  public ScoreService(
      IFinishingOrderService finishingOrderService,
      ITimedFinishingOrderService timedFinishingOrderService,
      IPointScoreService pointScoreService) {
    this.finishingOrderService = finishingOrderService;
    this.timedFinishingOrderService = timedFinishingOrderService;
    this.pointScoreService = pointScoreService;
  }

  @Override
  public void createNew(UUID eventId, ScoreType scoreType) {
    switch (scoreType) {
      case FINISHING_ORDER:
        finishingOrderService.createNew(eventId);
        break;
      case TIMED_FINISHING_ORDER:
        timedFinishingOrderService.createNew(eventId);
        break;
      case POINTS_SCORE_SHEET:
        pointScoreService.createNew(eventId);
        break;
    }
  }
}
