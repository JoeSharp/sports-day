package com.ratracejoe.sportsday.domain.fixtures;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class MemoryGenericRepository<T> implements IGenericRepository<T> {
  private final Class<T> clazz;
  private final Map<UUID, T> activities;
  private final Function<T, UUID> idExtractor;

  public MemoryGenericRepository(Class<T> clazz, Function<T, UUID> idExtractor) {
    this.clazz = clazz;
    this.activities = new HashMap<>();
    this.idExtractor = idExtractor;
  }

  @Override
  public T getById(UUID id) throws NotFoundException {
    checkUuidExists(id);
    return activities.get(id);
  }

  @Override
  public List<T> getAll() {
    return activities.values().stream().toList();
  }

  @Override
  public void save(T item) {
    activities.put(idExtractor.apply(item), item);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    checkUuidExists(id);
    activities.remove(id);
  }

  private void checkUuidExists(UUID id) throws NotFoundException {
    if (!activities.containsKey(id)) {
      throw new NotFoundException(this.clazz, id);
    }
  }
}
