package com.ratracejoe.sportsday.outgoing;

import java.util.List;
import java.util.UUID;

/**
 * A basic object repository has such a common form I captured it here.
 *
 * @param <T> The type being persisted
 * @param <E> The exception thrown if an object is expected to exist, but does not
 */
public interface IGenericRepository<T, E extends Exception> {
  T getById(UUID id) throws E;

  List<T> getAll();

  void save(T item);

  void deleteById(UUID id) throws E;
}
