package com.ratracejoe.sportsday.domain.outgoing;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.outgoing.IActivityRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MemoryActivityRepository implements IActivityRepository {
    private final Map<UUID, Activity> activities = new HashMap<>();

    @Override
    public Activity getByUuid(UUID id) throws ActivityNotFoundException {
        checkUuidExists(id);
        return activities.get(id);
    }

    @Override
    public List<Activity> getAll() {
        return activities.values().stream().toList();
    }

    @Override
    public void saveActivity(Activity activity) {
        activities.put(activity.id(), activity);
    }

    @Override
    public void deleteByUuid(UUID id) throws ActivityNotFoundException {
        checkUuidExists(id);
        activities.remove(id);
    }

    private void checkUuidExists(UUID id) throws ActivityNotFoundException {
        if (!activities.containsKey(id)) {
            throw new ActivityNotFoundException(id);
        }
    }
}
