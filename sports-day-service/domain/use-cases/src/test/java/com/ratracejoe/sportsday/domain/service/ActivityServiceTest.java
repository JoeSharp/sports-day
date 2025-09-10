package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.MemoryAdapters;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.memory.MemoryAuditLogger;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityServiceTest {
  private MemoryAuditLogger auditLogger;
  private ActivityService activityService;

  @BeforeEach
  void beforeEach() {
    MemoryAdapters memoryAdapters = new MemoryAdapters();
    auditLogger = memoryAdapters.auditLogger();
    activityService = memoryAdapters.activityService();
  }

  @Test
  void getByIdAuditsCorrectly() throws NotFoundException {
    // Given
    Activity activity = activityService.createActivity("Swimming", "flapping in water");

    // When
    Activity found = activityService.getById(activity.id());

    // Then
    assertThat(activity).isEqualTo(found);
    assertThat(auditLogger.getMessages())
        .containsExactly(
            "Activity 'Swimming' created with ID: " + activity.id(),
            "Activity " + activity.id() + " read");
  }

  @Test
  void getByIdFailsCorrectly() {
    // Given
    UUID id = UUID.randomUUID();

    // When, Then
    assertThatThrownBy(() -> activityService.getById(id)).isInstanceOf(NotFoundException.class);
    assertThat(auditLogger.getMessages()).containsExactly("Failed to read Activity " + id);
  }

  @Test
  void deleteByUuidSucceeds() throws NotFoundException {
    // Given
    Activity activity = activityService.createActivity("Swimming", "flapping in water");
    assertThat(activityService.getById(activity.id()))
        .extracting(Activity::name)
        .isEqualTo("Swimming");

    // When
    activityService.deleteByUuid(activity.id());

    // Then
    assertThatThrownBy(() -> activityService.getById(activity.id()))
        .isInstanceOf(NotFoundException.class);

    assertThat(auditLogger.getMessages())
        .containsExactly(
            "Activity 'Swimming' created with ID: " + activity.id(),
            "Activity " + activity.id() + " read",
            "Activity " + activity.id() + " deleted",
            "Failed to read Activity " + activity.id());
  }

  @Test
  void deleteByUuidFailsCorrectly() {
    // Given
    UUID id = UUID.randomUUID();

    // When, Then
    assertThatThrownBy(() -> activityService.deleteByUuid(id))
        .isInstanceOf(NotFoundException.class);

    assertThat(auditLogger.getMessages()).containsExactly("Failed to delete Activity " + id);
  }
}
