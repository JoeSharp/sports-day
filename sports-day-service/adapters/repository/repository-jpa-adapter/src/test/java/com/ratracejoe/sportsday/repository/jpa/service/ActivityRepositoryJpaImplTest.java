package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Activity;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class ActivityRepositoryJpaImplTest {

  @Autowired private ActivityRepositoryJpaImpl activityRepository;

  @Test
  void saveAndGet() {
    // Given
    Activity activity =
        new Activity(UUID.randomUUID(), "Swimming", "Propelling oneself through water innit");

    // When
    activityRepository.save(activity);
    Activity found = activityRepository.getById(activity.id());

    // Then
    assertThat(found).isEqualTo(activity);
  }
}
