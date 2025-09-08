package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.MemoryGenericRepository;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.IActivityRepository;
import java.util.List;
import java.util.UUID;

public class MemoryActivityRepository implements IActivityRepository {
  private final MemoryGenericRepository<Activity, NotFoundException> genericRepository;

  public MemoryActivityRepository() {
    this.genericRepository = new MemoryGenericRepository<>(Activity::id, NotFoundException::new);
  }

  @Override
  public Activity getById(UUID id) throws NotFoundException {
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
  public void deleteById(UUID id) throws NotFoundException {
    genericRepository.deleteById(id);
  }
}
