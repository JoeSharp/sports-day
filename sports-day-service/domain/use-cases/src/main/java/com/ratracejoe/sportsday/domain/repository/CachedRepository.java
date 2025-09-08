package com.ratracejoe.sportsday.domain.repository;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import java.util.List;
import java.util.UUID;

public abstract class CachedRepository<T, R extends IGenericRepository<T>>
    implements IGenericRepository<T> {
  private final R persistentRepository;
  private final R cacheRepository;

  protected CachedRepository(R persistentRepository, R cacheRepository) {
    this.persistentRepository = persistentRepository;
    this.cacheRepository = cacheRepository;
  }

  @Override
  public T getById(UUID id) throws NotFoundException {
    try {
      return cacheRepository.getById(id);
    } catch (NotFoundException e) {
      T item = persistentRepository.getById(id);
      cacheRepository.save(item);
      return item;
    }
  }

  @Override
  public List<T> getAll() {
    List<T> items = cacheRepository.getAll();
    if (items.isEmpty()) {
      items = persistentRepository.getAll();
      items.forEach(cacheRepository::save);
    }
    return items;
  }

  @Override
  public void save(T item) {
    cacheRepository.save(item);
    persistentRepository.save(item);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    try {
      cacheRepository.deleteById(id);
    } catch (NotFoundException e) {
      // Not a big deal
    }
    persistentRepository.deleteById(id);
  }
}
