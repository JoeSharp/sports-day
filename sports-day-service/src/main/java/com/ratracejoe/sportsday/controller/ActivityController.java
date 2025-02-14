package com.ratracejoe.sportsday.controller;

import com.ratracejoe.sportsday.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.model.rest.ActivityDTO;
import com.ratracejoe.sportsday.service.ActivityService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ActivityController {
  private final ActivityService activityService;

  @GetMapping("/{id}")
  public ActivityDTO getActivity(@PathVariable UUID id) throws ActivityNotFoundException {
    return activityService.getActivity(id);
  }

  @GetMapping
  public List<ActivityDTO> getActivities() {
    return activityService.getActivities();
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public ActivityDTO createActivity(@RequestBody ActivityDTO newActivity) {
    return activityService.createActivity(newActivity);
  }

  @DeleteMapping("/{id}")
  public void deleteActivity(@PathVariable UUID id) throws ActivityNotFoundException {
    activityService.deleteActivity(id);
  }
}
