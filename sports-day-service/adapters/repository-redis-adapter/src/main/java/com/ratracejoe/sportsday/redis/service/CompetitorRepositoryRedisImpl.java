package com.ratracejoe.sportsday.redis.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.ports.outgoing.ICompetitorRepository;
import com.ratracejoe.sportsday.redis.model.CachedCompetitor;
import com.ratracejoe.sportsday.redis.repository.CompetitorRedisCache;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompetitorRepositoryRedisImpl implements ICompetitorRepository {
  private final CompetitorRedisCache cache;

  @Override
  public Competitor getById(UUID id) throws NotFoundException {
    return cache
        .findById(id)
        .map(CompetitorRepositoryRedisImpl::cacheToDomain)
        .orElseThrow(() -> new NotFoundException(Competitor.class, id));
  }

  @Override
  public List<Competitor> getAll() {
    return StreamSupport.stream(cache.findAll().spliterator(), true)
        .map(CompetitorRepositoryRedisImpl::cacheToDomain)
        .toList();
  }

  @Override
  public void save(Competitor item) {
    cache.save(domainToCache(item));
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    cache.deleteById(id);
  }

  private static CachedCompetitor domainToCache(Competitor domain) {
    return new CachedCompetitor(domain.id(), domain.name());
  }

  private static Competitor cacheToDomain(CachedCompetitor cached) {
    return new Competitor(cached.getId(), cached.getName());
  }
}
