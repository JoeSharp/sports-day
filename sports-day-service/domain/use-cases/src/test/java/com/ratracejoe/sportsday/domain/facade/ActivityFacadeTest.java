package com.ratracejoe.sportsday.domain.facade;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.fixtures.FixtureFactory;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.outgoing.MemoryAuditLogger;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ActivityFacadeTest {
  private MemoryAuditLogger auditLogger;
  private ActivityFacade activityFacade;

  @BeforeEach
  void beforeEach() {
    FixtureFactory fixtureFactory = new FixtureFactory();
    auditLogger = fixtureFactory.auditLogger();
    activityFacade = fixtureFactory.activityFacade();
  }

  @Test
  void getByIdAuditsCorrectly() throws NotFoundException {
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
    assertThatThrownBy(() -> activityFacade.getById(id)).isInstanceOf(NotFoundException.class);
    assertThat(auditLogger.getMessages()).containsExactly("Failed to read Activity " + id);
  }

  @Test
  void deleteByUuidSucceeds() throws NotFoundException {
    // Given
    Activity activity = activityFacade.createActivity("Swimming", "flapping in water");
    assertThat(activityFacade.getById(activity.id()))
        .extracting(Activity::name)
        .isEqualTo("Swimming");

    // When
    activityFacade.deleteByUuid(activity.id());

    // Then
    assertThatThrownBy(() -> activityFacade.getById(activity.id()))
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
    assertThatThrownBy(() -> activityFacade.deleteByUuid(id)).isInstanceOf(NotFoundException.class);

    assertThat(auditLogger.getMessages()).containsExactly("Failed to delete Activity " + id);
  }
}
