package com.ratracejoe.sportsday.domain.model;

import com.ratracejoe.sportsday.domain.exception.InvalidEventStateException;

public enum EventState {
  CREATING,
  STARTED,
  FINISHED;

  EventState nextStateIsValid(EventState nextState) throws InvalidEventStateException {
    if (CREATING.equals(this)) {
      if (!STARTED.equals(nextState)) {
        throw new InvalidEventStateException();
      }
    } else {
      if (!FINISHED.equals(nextState)) {
        throw new InvalidEventStateException();
      }
    }
    return nextState;
  }
}
