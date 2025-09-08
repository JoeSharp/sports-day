package com.ratracejoe.sportsday.redis.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.outgoing.ITeamRepository;
import com.ratracejoe.sportsday.redis.model.CachedTeam;
import com.ratracejoe.sportsday.redis.repository.TeamRedisCache;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TeamRepositoryRedisImpl implements ITeamRepository {
  private final TeamRedisCache cache;

  @Override
  public Team getById(UUID id) throws NotFoundException {
    return cache
        .findById(id)
        .map(TeamRepositoryRedisImpl::cacheToDomain)
        .orElseThrow(() -> new NotFoundException(Team.class, id));
  }

  @Override
  public List<Team> getAll() {
    return StreamSupport.stream(cache.findAll().spliterator(), true)
        .map(TeamRepositoryRedisImpl::cacheToDomain)
        .toList();
  }

  @Override
  public void save(Team item) {
    cache.save(domainToCache(item));
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    cache.deleteById(id);
  }

  private static CachedTeam domainToCache(Team domain) {
    return new CachedTeam(domain.id(), domain.name());
  }

  private static Team cacheToDomain(CachedTeam cached) {
    return new Team(cached.getId(), cached.getName());
  }
}
