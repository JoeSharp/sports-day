package com.ratracejoe.sportsday.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ratracejoe.sportsday.domain.model.Activity;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class ActivityCommandServiceTest extends GenericCommandServiceTest {
  @Test
  void createActivity() throws InvalidCommandException {
    // When
    commandService.handleCommand("activity add swimming \"in water init\"");

    // Then
    verify(activityService).createActivity("swimming", "in water init");
  }

  @Test
  void getById() throws InvalidCommandException {
    // When
    Activity activity = new Activity(UUID.randomUUID(), "running", "getting sweaty");
    when(activityService.getById(activity.id())).thenReturn(activity);
    commandService.handleCommand("activity getById " + activity.id());

    // Then
    verify(activityService, times(1)).getById(activity.id());
    verify(responseListener, times(1)).handleResponse(any(String.class));
  }

  @Test
  void getAll() throws InvalidCommandException {
    // When
    List<Activity> domainList =
        List.of(
            new Activity(UUID.randomUUID(), "running", "getting sweaty"),
            new Activity(UUID.randomUUID(), "swimming", "getting wet"),
            new Activity(UUID.randomUUID(), "sky diving", "go splat"));
    when(activityService.getAll()).thenReturn(domainList);
    commandService.handleCommand("activity getAll");

    // Then
    verify(activityService, times(1)).getAll();
    verify(responseListener, times(3)).handleResponse(any(String.class));
  }

  @Test
  void deleteById() throws InvalidCommandException {
    // Given
    UUID id = UUID.randomUUID();

    // When
    commandService.handleCommand("activity deleteById " + id);

    // Then
    verify(activityService).deleteById(id);
  }

  @Test
  void getAllInvalid() {
    // When
    assertThatThrownBy(() -> commandService.handleCommand("activity getAll foobar"))
        .isInstanceOf(InvalidCommandException.class);
  }
}
