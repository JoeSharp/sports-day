package com.ratracejoe.sportsday.ports.incoming.auth;

import com.ratracejoe.sportsday.domain.auth.SportsDayRole;
import com.ratracejoe.sportsday.domain.auth.SportsDayUser;
import com.ratracejoe.sportsday.domain.exception.UnauthorisedException;
import java.util.Objects;

public interface ISportsDayUserSupplier {
  SportsDayUser getUser();

  default void userHasRole(SportsDayRole role) throws UnauthorisedException {
    userHasRoles(role);
  }

  default void userHasRoles(SportsDayRole... roles) throws UnauthorisedException {
    SportsDayUser user = getUser();
    if (Objects.isNull(user)) {
      throw new UnauthorisedException();
    }
    for (SportsDayRole role : roles) {
      if (!user.roles().contains(role)) {
        throw new UnauthorisedException();
      }
    }
  }
}
