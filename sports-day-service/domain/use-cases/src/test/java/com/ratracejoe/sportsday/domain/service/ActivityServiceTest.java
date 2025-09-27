package com.ratracejoe.sportsday.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ratracejoe.sportsday.domain.SportsTestFixtures;
import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ActivityServiceTest {
  private SportsTestFixtures fixtures;
  private IActivityService activityService;

  @BeforeEach
  void beforeEach() {
    fixtures = new SportsTestFixtures();
    activityService = fixtures.memoryAdapters().activityService();
  }

  @Test
  void getByIdAuditsCorrectly() throws NotFoundException {
    // Given
    Activity activity = fixtures.activityCreated();

    // When
    Activity found = activityService.getById(activity.id());

    // Then
    assertThat(activity).isEqualTo(found);
    assertThat(fixtures.memoryAdapters().getAuditMessages())
        .containsExactly(
            "Activity '" + activity.name() + "' created with ID: " + activity.id(),
            "Activity " + activity.id() + " read");
  }

  @Test
  void getByIdFailsCorrectly() {
    // Given
    UUID id = UUID.randomUUID();

    // When, Then
    assertThatThrownBy(() -> activityService.getById(id)).isInstanceOf(NotFoundException.class);
    assertThat(fixtures.memoryAdapters().getAuditMessages())
        .containsExactly("Failed to read Activity " + id);
  }

  static Stream<SportsDayRole> nonAuthorisedActivityCreators() {
    return Stream.of(SportsDayRole.STUDENT, SportsDayRole.FORM_TUTOR, SportsDayRole.VOLUNTEER);
  }

  @ParameterizedTest
  @MethodSource("nonAuthorisedActivityCreators")
  void nonAuthorisedUsersCannotCreateActivity(SportsDayRole role) {
    // Given
    fixtures
        .memoryAdapters()
        .setCurrentUser(new SportsDayUser(UUID.randomUUID().toString(), "Test", List.of(role)));

    assertThatThrownBy(() -> activityService.createActivity("anything", "goes"))
        .isInstanceOf(UnauthorisedException.class);
  }

  @Test
  void deleteByIdSucceeds() throws NotFoundException {
    // Given
    Activity activity = fixtures.activityCreated();
    UUID activityId = activity.id();
    assertThat(activityService.getById(activityId))
        .extracting(Activity::name)
        .isEqualTo(activity.name());

    // When
    activityService.deleteById(activity.id());

    // Then
    assertThatThrownBy(() -> activityService.getById(activityId))
        .isInstanceOf(NotFoundException.class);

    assertThat(fixtures.memoryAdapters().getAuditMessages())
        .containsExactly(
            "Activity '" + activity.name() + "' created with ID: " + activity.id(),
            "Activity " + activity.id() + " read",
            "Activity " + activity.id() + " deleted",
            "Failed to read Activity " + activity.id());
  }

  @Test
  void deleteByIdFailsCorrectly() {
    // Given
    UUID id = UUID.randomUUID();

    // When, Then
    assertThatThrownBy(() -> activityService.deleteById(id))
        .isInstanceOf(NotFoundException.class);

    assertThat(fixtures.memoryAdapters().getAuditMessages())
        .containsExactly("Failed to delete Activity " + id);
  }
}
