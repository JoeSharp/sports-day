package com.ratracejoe.sportsday.web.controller;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.incoming.IActivityFacade;
import com.ratracejoe.sportsday.web.model.rest.ActivityDTO;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ActivityController {
  private final IActivityFacade activityService;

  @GetMapping("/{id}")
  public ActivityDTO getActivity(@PathVariable UUID id) throws ActivityNotFoundException {
    return ActivityDTO.fromDomain(activityService.getByUuid(id));
  }

  @GetMapping
  public List<ActivityDTO> getActivities() {
    return activityService.getAll().stream().map(ActivityDTO::fromDomain).toList();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ActivityDTO createActivity(@RequestBody ActivityDTO newActivity) {
    return ActivityDTO.fromDomain(
        activityService.createActivity(newActivity.name(), newActivity.description()));
  }

  @DeleteMapping("/{id}")
  public void deleteActivity(@PathVariable UUID id) throws ActivityNotFoundException {
    activityService.deleteByUuid(id);
  }
}
