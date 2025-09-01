package com.ratracejoe.sportsday.domain.service;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.domain.outgoing.MemoryActivityRepository;
import com.ratracejoe.sportsday.domain.outgoing.MemoryAuditLogger;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;
import com.ratracejoe.sportsday.outgoing.IAuditLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ActivityFacadeTest {
    MemoryActivityRepository repository;
    MemoryAuditLogger auditLogger;
    AuditService auditService;
    ActivityFacade activityFacade;

    @BeforeEach
    void beforeEach() {
         repository = new MemoryActivityRepository();
         auditLogger = new MemoryAuditLogger();
         auditService = new AuditService(auditLogger);
         activityFacade = new ActivityFacade(repository, auditService);
    }

    @Test
    void getByUuidAuditsCorrectly() throws ActivityNotFoundException {
        // Given
        Activity activity = activityFacade.createActivity("Swimming", "flapping in water");

        // When
        Activity found = activityFacade.getByUuid(activity.id());

        // Then
        assertThat(activity).isEqualTo(found);
        assertThat(auditLogger.getMessages())
                .containsExactly(
                        "Activity Swimming created with ID: " + activity.id(),
                        "Activity " + activity.id() + " read");
    }

    @Test
    void getByUuidFailsCorrectly() {
        // Given
        UUID id = UUID.randomUUID();

        // When, Then
        assertThatThrownBy(() -> activityFacade.getByUuid(id))
                .isInstanceOf(ActivityNotFoundException.class);
        assertThat(auditLogger.getMessages())
                .containsExactly("Failed to read Activity " + id);
    }

    @Test
    void deleteByUuidSucceeds() throws ActivityNotFoundException {
        // Given
        Activity activity = activityFacade.createActivity("Swimming", "flapping in water");
        assertThat(activityFacade.getByUuid(activity.id()))
                .extracting(Activity::name)
                .isEqualTo("Swimming");

        // When
        activityFacade.deleteByUuid(activity.id());

        // Then
        assertThatThrownBy(() -> activityFacade.getByUuid(activity.id()))
                .isInstanceOf(ActivityNotFoundException.class);

        assertThat(auditLogger.getMessages())
                .containsExactly(
                        "Activity Swimming created with ID: " + activity.id(),
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

        assertThat(auditLogger.getMessages())
                .containsExactly("Failed to delete Activity " + id);
    }
}