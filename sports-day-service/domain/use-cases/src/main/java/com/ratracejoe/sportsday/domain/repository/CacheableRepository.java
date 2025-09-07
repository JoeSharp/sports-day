package com.ratracejoe.sportsday.domain.repository;

import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import java.util.List;
import java.util.UUID;

public class CacheableRepository<T, E extends Exception> implements IGenericRepository<T, E> {
  private final IGenericRepository<T, E> slowRepository;
  private final IGenericRepository<T, E> cacheRepository;

  public CacheableRepository(IGenericRepository<T, E> slow, IGenericRepository<T, E> cache) {
    this.slowRepository = slow;
    this.cacheRepository = cache;
  }

  public List<T> populateCache() {
    List<T> items = slowRepository.getAll();
    items.forEach(cacheRepository::save);
    return items;
  }

  @Override
  public T getById(UUID id) throws E {
    try {
      return cacheRepository.getById(id);
    } catch (Exception e) {
      T item = slowRepository.getById(id);
      cacheRepository.save(item);
      return item;
    }
  }

  @Override
  public List<T> getAll() {
    List<T> cached = cacheRepository.getAll();
    if (cached.isEmpty()) {
      cached = populateCache();
    }
    return cached;
  }

  @Override
  public void save(T item) {
    cacheRepository.save(item);
    slowRepository.save(item);
  }

  @Override
  public void deleteById(UUID id) throws E {
    cacheRepository.deleteById(id);
    slowRepository.deleteById(id);
  }
}
