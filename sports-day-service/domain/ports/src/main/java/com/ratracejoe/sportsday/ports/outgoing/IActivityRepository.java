package com.ratracejoe.sportsday.ports.outgoing;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;

public interface IActivityRepository
    extends IGenericRepository<Activity, ActivityNotFoundException> {}
