package com.ratracejoe.sportsday.auth;

import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;

public class MemorySportsDayUserSupplier implements ISportsDayUserSupplier {
  private SportsDayUser currentUser;

  @Override
  public SportsDayUser getUser() {
    return currentUser;
  }

  public void setCurrentUser(SportsDayUser currentUser) {
    this.currentUser = currentUser;
  }
}
