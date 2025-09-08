package com.ratracejoe.sportsday.rest.controller;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.ports.incoming.IActivityFacade;
import com.ratracejoe.sportsday.rest.model.ActivityDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ActivityController {
  private final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);
  private final IActivityFacade activityService;

  @GetMapping("/{id}")
  public ActivityDTO getActivity(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Retrieving Activity by {}", id);
    return ActivityDTO.fromDomain(activityService.getById(id));
  }

  @GetMapping
  public List<ActivityDTO> getActivities() {
    return activityService.getAll().stream().map(ActivityDTO::fromDomain).toList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ActivityDTO createActivity(@RequestBody ActivityDTO newActivity) {
    var activity =
        ActivityDTO.fromDomain(
            activityService.createActivity(newActivity.name(), newActivity.description()));
    LOGGER.info("Created Activity {}", activity);
    return activity;
  }

  @DeleteMapping("/{id}")
  public void deleteActivity(@PathVariable UUID id) throws NotFoundException {
    LOGGER.info("Deleting activity {}", id);
    activityService.deleteByUuid(id);
  }
}
