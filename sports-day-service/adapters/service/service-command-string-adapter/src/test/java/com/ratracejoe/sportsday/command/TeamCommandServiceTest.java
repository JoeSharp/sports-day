package com.ratracejoe.sportsday.command;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.ratracejoe.sportsday.domain.model.Team;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class TeamCommandServiceTest extends GenericCommandServiceTest {
  @Test
  void createTeam() throws InvalidCommandException {
    // When
    commandService.handleCommand("team create \"Water Fowls\"");

    // Then
    verify(teamService).createTeam("Water Fowls");
  }

  @Test
  void getById() throws InvalidCommandException {
    // When
    Team team = new Team(UUID.randomUUID(), "Pixie Ninjas");
    when(teamService.getById(team.id())).thenReturn(team);
    commandService.handleCommand("team getById " + team.id());

    // Then
    verify(teamService, times(1)).getById(team.id());
    verify(responseListener, times(1)).handleResponse(any(String.class));
  }

  @Test
  void getAll() throws InvalidCommandException {
    // When
    List<Team> domainList =
        List.of(
            new Team(UUID.randomUUID(), "Fruit Shooters"),
            new Team(UUID.randomUUID(), "Veg Eviscerators"),
            new Team(UUID.randomUUID(), "Bread Slashers"));
    when(teamService.getAll()).thenReturn(domainList);
    commandService.handleCommand("team getAll");

    // Then
    verify(teamService, times(1)).getAll();
    verify(responseListener, times(3)).handleResponse(any(String.class));
  }

  @Test
  void deleteById() throws InvalidCommandException {
    // Given
    UUID id = UUID.randomUUID();

    // When
    commandService.handleCommand("team deleteById " + id);

    // Then
    verify(teamService).deleteById(id);
  }

  @Test
  void getAllInvalid() {
    // When
    assertThatThrownBy(() -> commandService.handleCommand("team getAll foobar"))
        .isInstanceOf(InvalidCommandException.class);
  }
}
