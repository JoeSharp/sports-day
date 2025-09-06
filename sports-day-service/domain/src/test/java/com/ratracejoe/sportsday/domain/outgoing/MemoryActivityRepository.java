package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;
import java.util.List;
import java.util.UUID;

public class MemoryActivityRepository implements IActivityRepository {
  private final MemoryGenericRepository<Activity, ActivityNotFoundException> genericRepository;

  public MemoryActivityRepository() {
    this.genericRepository =
        new MemoryGenericRepository<>(Activity::id, ActivityNotFoundException::new);
  }

  @Override
  public Activity getById(UUID id) throws ActivityNotFoundException {
    return genericRepository.getById(id);
  }

  @Override
  public List<Activity> getAll() {
    return genericRepository.getAll();
  }

  @Override
  public void save(Activity activity) {
    genericRepository.save(activity);
  }

  @Override
  public void deleteById(UUID id) throws ActivityNotFoundException {
    genericRepository.deleteById(id);
  }
}
