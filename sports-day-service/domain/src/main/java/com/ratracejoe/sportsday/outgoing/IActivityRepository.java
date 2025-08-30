package com.ratracejoe.sportsday.outgoing;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;

import java.util.List;
import java.util.UUID;

public interface IActivityRepository {
    Activity getByUuid(UUID id) throws ActivityNotFoundException;
    List<Activity> getAll();
    void saveActivity(Activity activity);
    void deleteByUuid(UUID id) throws ActivityNotFoundException;
}
