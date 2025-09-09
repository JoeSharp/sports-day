package com.ratracejoe.sportsday.domain.fixtures;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class MemoryGenericRepository<T> implements IGenericRepository<T> {
  private final Class<T> clazz;
  private final Map<UUID, T> entities;
  private final Function<T, UUID> idExtractor;
  private final List<UUID> callsToGetById;
  private final AtomicInteger callsToGetAll;
  private final List<T> callsToSave;
  private final List<UUID> callsToDelete;

  public List<UUID> getCallsToGetById() {
    return callsToGetById.stream().toList();
  }

  public int getCallsToGetAll() {
    return callsToGetAll.get();
  }

  public List<T> callsToSave() {
    return callsToSave.stream().toList();
  }

  public List<UUID> getCallsToDelete() {
    return callsToDelete.stream().toList();
  }

  public MemoryGenericRepository(Class<T> clazz, Function<T, UUID> idExtractor) {
    this.clazz = clazz;
    this.entities = new HashMap<>();
    this.idExtractor = idExtractor;
    this.callsToGetById = new ArrayList<>();
    this.callsToGetAll = new AtomicInteger(0);
    this.callsToSave = new ArrayList<>();
    this.callsToDelete = new ArrayList<>();
  }

  public void resetCalls() {
    callsToGetById.clear();
    callsToGetAll.set(0);
    callsToSave.clear();
    callsToDelete.clear();
  }

  @Override
  public T getById(UUID id) throws NotFoundException {
    callsToGetById.add(id);
    checkUuidExists(id);
    return entities.get(id);
  }

  @Override
  public List<T> getAll() {
    callsToGetAll.incrementAndGet();
    return entities.values().stream().toList();
  }

  @Override
  public void save(T item) {
    callsToSave.add(item);
    entities.put(idExtractor.apply(item), item);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    callsToDelete.add(id);
    checkUuidExists(id);
    entities.remove(id);
  }

  private void checkUuidExists(UUID id) throws NotFoundException {
    if (!entities.containsKey(id)) {
      throw new NotFoundException(this.clazz, id);
    }
  }
}
