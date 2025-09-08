package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.IGenericRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenericRepositoryJpaImpl<T, E> implements IGenericRepository<T> {
  private final JpaRepository<E, UUID> repository;
  private final Function<T, E> toEntity;
  private final Function<E, T> toDomain;

  @Override
  public T getById(UUID id) throws NotFoundException {
    return repository
        .findById(id)
        .map(toDomain)
        .orElseThrow(() -> new NotFoundException(Activity.class, id));
  }

  @Override
  public List<T> getAll() {
    return repository.findAll().stream().map(toDomain).toList();
  }

  @Override
  @Transactional
  public void save(T domain) {
    E entity = toEntity.apply(domain);
    repository.save(entity);
  }

  @Override
  @Transactional
  public void deleteById(UUID id) throws NotFoundException {
    if (!repository.existsById(id)) {
      throw new NotFoundException(Activity.class, id);
    }
    repository.deleteById(id);
  }
}
