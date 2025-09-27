package com.ratracejoe.sportsday.command.services;

import com.ratracejoe.sportsday.command.Command;
import com.ratracejoe.sportsday.command.IResponseListener;
import com.ratracejoe.sportsday.command.InvalidCommandException;
import com.ratracejoe.sportsday.domain.model.Activity;
import com.ratracejoe.sportsday.ports.incoming.service.IActivityService;
import java.util.List;

public class ActivityCommandService extends GenericCommandService<Activity> {
  private final IActivityService activityService;

  public ActivityCommandService(
      final IActivityService activityService, final IResponseListener responseListener) {
    super(responseListener);
    this.activityService = activityService;

    registerHandler("add", this::addActivity);
    registerHandler("getAll", getAllHandler(activityService::getAll));
    registerHandler("getById", getByIdHandler(activityService::getById));
    registerHandler("deleteById", deleteHandler(activityService::deleteById));
  }

  private void addActivity(String input) throws InvalidCommandException {
    List<String> parts = Command.splitRespectingQuotes(input);
    if (parts.size() != 2) {
      throw new InvalidCommandException();
    }
    activityService.createActivity(parts.get(0), parts.get(1));
  }

  @Override
  String domainToLogString(Activity activity) {
    return String.format(
        "Activity id: '%s', name: '%s', description: '%s'",
        activity.id(), activity.name(), activity.description());
  }
}
