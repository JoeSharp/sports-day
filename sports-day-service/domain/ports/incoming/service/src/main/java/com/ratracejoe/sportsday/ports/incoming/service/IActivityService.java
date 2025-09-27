package com.ratracejoe.sportsday.ports.incoming.service;

import com.ratracejoe.sportsday.domain.exception.NotFoundException;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import com.ratracejoe.sportsday.domain.model.Activity;
import java.util.List;
import java.util.UUID;

public interface IActivityService {

  Activity getById(UUID id) throws NotFoundException;

  List<Activity> getAll();

  Activity createActivity(String name, String description) throws UnauthorisedException;

  void deleteById(UUID id) throws NotFoundException, UnauthorisedException;
}
