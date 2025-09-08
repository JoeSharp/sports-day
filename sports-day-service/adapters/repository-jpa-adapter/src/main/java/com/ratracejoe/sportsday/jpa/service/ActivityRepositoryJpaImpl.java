package com.ratracejoe.sportsday.jpa.service;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.jpa.model.ActivityEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public class ActivityRepositoryJpaImpl extends GenericRepositoryJpaImpl<Activity, ActivityEntity> {
  public ActivityRepositoryJpaImpl(JpaRepository<ActivityEntity, UUID> repository) {
    super(repository, ActivityEntity::domainToEntity, ActivityEntity::entityToDomain);
  }
}
