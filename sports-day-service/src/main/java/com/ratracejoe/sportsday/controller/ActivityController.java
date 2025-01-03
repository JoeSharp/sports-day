package com.ratracejoe.sportsday.controller;

import com.ratracejoe.sportsday.model.ActivityDTO;
import com.ratracejoe.sportsday.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/activities", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityService activityService;

    @GetMapping
    public List<ActivityDTO> getActivities() {
        return activityService.getActivities();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ActivityDTO createActivity(@RequestBody ActivityDTO newActivity) {
        return activityService.createActivity(newActivity);
    }
}
