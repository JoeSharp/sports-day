package com.ratracejoe.sportsday.domain.repository;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;

public class ActivityCachedRepository extends CachedRepository<Activity, IActivityRepository>
    implements IActivityRepository {
  public ActivityCachedRepository(IActivityRepository persistent, IActivityRepository cached) {
    super(persistent, cached);
  }
}
