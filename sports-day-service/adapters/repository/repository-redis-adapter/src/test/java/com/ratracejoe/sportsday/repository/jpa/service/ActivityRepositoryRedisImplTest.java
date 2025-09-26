package com.ratracejoe.sportsday.repository.jpa.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.repository.redis.service.ActivityRepositoryRedisImpl;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ExtendWith(RedisTestContainerExtension.class)
class ActivityRepositoryRedisImplTest {

  @DynamicPropertySource
  static void registerDynamicProperties(DynamicPropertyRegistry registry) {
    RedisTestContainerExtension.registerDynamicProperties(registry);
  }

  @Autowired private ActivityRepositoryRedisImpl activityRepository;

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
