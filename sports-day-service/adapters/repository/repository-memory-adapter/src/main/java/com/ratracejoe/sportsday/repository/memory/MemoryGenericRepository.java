package com.ratracejoe.sportsday.repository.memory;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.outgoing.repository.IGenericRepository;
import java.util.*;
import java.util.function.Function;

public class MemoryGenericRepository<T> implements IGenericRepository<T> {
  private final Class<T> clazz;
  private final Map<UUID, T> entities;
  private final Function<T, UUID> idExtractor;

  public MemoryGenericRepository(Class<T> clazz, Function<T, UUID> idExtractor) {
    this.clazz = clazz;
    this.entities = new HashMap<>();
    this.idExtractor = idExtractor;
  }

  @Override
  public T getById(UUID id) throws NotFoundException {
    checkUuidExists(id);
    return entities.get(id);
  }

  @Override
  public List<T> getAll() {
    return entities.values().stream().toList();
  }

  @Override
  public void save(T item) {
    entities.put(idExtractor.apply(item), item);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    checkUuidExists(id);
    entities.remove(id);
  }

  private void checkUuidExists(UUID id) throws NotFoundException {
    if (!entities.containsKey(id)) {
      throw new NotFoundException(this.clazz, id);
    }
  }
}
