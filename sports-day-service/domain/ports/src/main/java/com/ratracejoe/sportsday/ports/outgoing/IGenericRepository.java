package com.ratracejoe.sportsday.ports.outgoing;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import java.util.List;
import java.util.UUID;

/**
 * A basic object repository has such a common form I captured it here.
 *
 * @param <T> The type being persisted
 */
public interface IGenericRepository<T> {
  T getById(UUID id) throws NotFoundException;

  List<T> getAll();

  void save(T item);

  void deleteById(UUID id) throws NotFoundException;
}
