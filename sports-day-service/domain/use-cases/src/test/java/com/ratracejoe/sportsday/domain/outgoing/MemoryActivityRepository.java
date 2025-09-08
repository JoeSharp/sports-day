package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;

public class MemoryActivityRepository extends MemoryGenericRepository<Activity>
    implements IActivityRepository {

  public MemoryActivityRepository() {
    super(Activity.class, Activity::id);
  }
}
