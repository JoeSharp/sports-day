package com.ratracejoe.sportsday.rest.controller;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import com.ratracejoe.sportsday.rest.model.NewTeamDTO;
import com.ratracejoe.sportsday.rest.model.TeamDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/teams", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TeamController {
  private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);
  private final ITeamService teamService;

  @GetMapping("/{id}")
  public TeamDTO getById(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Retrieving Team by {}", id);
    Team team = teamService.getById(id);
    return enrichTeam(team);
  }

  @GetMapping
  public List<TeamDTO> getAll() {
    return teamService.getAll().stream().map(this::enrichTeam).toList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public TeamDTO createTeam(@RequestBody NewTeamDTO newTeam) {
    var team = teamService.createTeam(newTeam.name());
    LOGGER.info("Created Team {}", team);
    return enrichTeam(team);
  }

  @PostMapping("/{teamId}/registerMember/{competitorId}")
  public TeamDTO registerMember(@PathVariable UUID teamId, @PathVariable UUID competitorId) {
    teamService.registerMember(teamId, competitorId);
    return getById(teamId);
  }

  @DeleteMapping("/{id}")
  public void deleteTeam(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Deleting team {}", id);
    teamService.deleteById(id);
  }

  private TeamDTO enrichTeam(Team team) {
    List<Competitor> members = teamService.getMembers(team.id());
    return TeamDTO.fromDomain(team, members);
  }
}
