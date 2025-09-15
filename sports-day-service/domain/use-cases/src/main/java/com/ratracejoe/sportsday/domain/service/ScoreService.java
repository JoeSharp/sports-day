package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.service.score.FinishingOrderService;
import com.ratracejoe.sportsday.domain.service.score.PointScoreService;
import com.ratracejoe.sportsday.domain.service.score.TimedFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.IScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;

public class ScoreService implements IScoreService {
  private final FinishingOrderService finishingOrderService;
  private final TimedFinishingOrderService timedFinishingOrderService;
  private final PointScoreService pointScoreService;

  public ScoreService(
      FinishingOrderService finishingOrderService,
      TimedFinishingOrderService timedFinishingOrderService,
      PointScoreService pointScoreService) {
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
