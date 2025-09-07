package com.ratracejoe.sportsday.domain.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.outgoing.MemoryActivityRepository;
import com.ratracejoe.sportsday.domain.outgoing.MemoryAuditLogger;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityFacadeTest {
  private MemoryAuditLogger auditLogger;
  private ActivityFacade activityFacade;

  @BeforeEach
  void beforeEach() {
    MemoryActivityRepository activityRepository = new MemoryActivityRepository();
    auditLogger = new MemoryAuditLogger();
    activityFacade = new ActivityFacade(activityRepository, auditLogger);
  }

  @Test
  void getByIdAuditsCorrectly() throws ActivityNotFoundException {
    // Given
    Activity activity = activityFacade.createActivity("Swimming", "flapping in water");

    // When
    Activity found = activityFacade.getById(activity.id());

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
    assertThatThrownBy(() -> activityFacade.getById(id))
        .isInstanceOf(ActivityNotFoundException.class);
    assertThat(auditLogger.getMessages()).containsExactly("Failed to read Activity " + id);
  }

  @Test
  void deleteByUuidSucceeds() throws ActivityNotFoundException {
    // Given
    Activity activity = activityFacade.createActivity("Swimming", "flapping in water");
    assertThat(activityFacade.getById(activity.id()))
        .extracting(Activity::name)
        .isEqualTo("Swimming");

    // When
    activityFacade.deleteByUuid(activity.id());

    // Then
    assertThatThrownBy(() -> activityFacade.getById(activity.id()))
        .isInstanceOf(ActivityNotFoundException.class);

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
    assertThatThrownBy(() -> activityFacade.deleteByUuid(id))
        .isInstanceOf(ActivityNotFoundException.class);

    assertThat(auditLogger.getMessages()).containsExactly("Failed to delete Activity " + id);
  }
}
