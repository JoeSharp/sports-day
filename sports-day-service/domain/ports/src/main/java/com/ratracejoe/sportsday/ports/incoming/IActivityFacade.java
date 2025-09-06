package com.ratracejoe.sportsday.ports.incoming;

import com.ratracejoe.sportsday.domain.exception.ActivityNotFoundException;
import com.ratracejoe.sportsday.domain.model.Activity;
import java.util.List;
import java.util.UUID;

public interface IActivityFacade {

  Activity getById(UUID id) throws ActivityNotFoundException;

  List<Activity> getAll();

  Activity createActivity(String name, String description);

  void deleteByUuid(UUID id) throws ActivityNotFoundException;
}
