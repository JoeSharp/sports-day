package com.ratracejoe.sportsday.repository.jpa.service;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.outgoing.repository.IActivityRepository;
import com.ratracejoe.sportsday.repository.jpa.entity.ActivityEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class ActivityRepositoryJpaImpl extends GenericRepositoryJpaImpl<Activity, ActivityEntity>
    implements IActivityRepository {
  public ActivityRepositoryJpaImpl(JpaRepository<ActivityEntity, UUID> repository) {
    super(repository, ActivityEntity::domainToEntity, ActivityEntity::entityToDomain);
  }
}
