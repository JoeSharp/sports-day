package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;

public class MemoryActivityRepository extends MemoryGenericRepository<Activity>
    implements IActivityRepository {

  public MemoryActivityRepository() {
    super(Activity.class, Activity::id);
  }
}
