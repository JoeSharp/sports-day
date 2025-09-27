package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;

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
  public ITimedFinishingOrderService timedFinishingOrderService() {
    return timedFinishingOrderService;
  }

  @Override
  public IFinishingOrderService finishingOrderService() {
    return finishingOrderService;
  }

  @Override
  public IPointScoreService pointScoreService() {
    return pointScoreService;
  }
}
