package com.ratracejoe.sportsday.rest.auth;

import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;
import java.util.List;
import java.util.UUID;

public class OAuthSportsDayUserSupplier implements ISportsDayUserSupplier {
  @Override
  public SportsDayUser getUser() {

    return new SportsDayUser(
        UUID.randomUUID().toString(), "Fake User", List.of(SportsDayRole.values()));
  }
}
