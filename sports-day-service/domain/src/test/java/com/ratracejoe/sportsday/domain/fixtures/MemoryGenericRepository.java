package com.ratracejoe.sportsday.domain.fixtures;

import com.ratracejoe.sportsday.outgoing.IGenericRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

public class MemoryGenericRepository<T, E extends Exception> implements IGenericRepository<T, E> {
  private final Map<UUID, T> activities;
  private final Function<T, UUID> idExtractor;
  private final Function<UUID, E> exceptionSupplier;

  public MemoryGenericRepository(
      Function<T, UUID> idExtractor, Function<UUID, E> exceptionSupplier) {
    this.activities = new HashMap<>();
    this.idExtractor = idExtractor;
    this.exceptionSupplier = exceptionSupplier;
  }

  @Override
  public T getById(UUID id) throws E {
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
  public void deleteById(UUID id) throws E {
    checkUuidExists(id);
    activities.remove(id);
  }

  private void checkUuidExists(UUID id) throws E {
    if (!activities.containsKey(id)) {
      throw this.exceptionSupplier.apply(id);
    }
  }
}
