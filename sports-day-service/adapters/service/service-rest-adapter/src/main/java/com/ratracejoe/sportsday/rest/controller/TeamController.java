package com.ratracejoe.sportsday.rest.controller;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.model.Competitor;
import com.ratracejoe.sportsday.domain.model.Team;
import com.ratracejoe.sportsday.ports.incoming.service.ITeamService;
import com.ratracejoe.sportsday.rest.model.TeamDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    List<Competitor> members = teamService.getMembers(id);
    return TeamDTO.fromDomain(team, members);
  }
}
