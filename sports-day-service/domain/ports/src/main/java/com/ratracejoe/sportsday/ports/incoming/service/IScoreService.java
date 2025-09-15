package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.ports.incoming.service.score.IFinishingOrderService;
import com.ratracejoe.sportsday.ports.incoming.service.score.IPointScoreService;
import com.ratracejoe.sportsday.ports.incoming.service.score.ITimedFinishingOrderService;

public interface IScoreService {
  ITimedFinishingOrderService timedFinishingOrderService();

  IFinishingOrderService finishingOrderService();

  IPointScoreService pointScoreService();
}
