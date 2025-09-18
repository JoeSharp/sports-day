package com.ratracejoe.sportsday.ports.incoming.auth;

import com.ratracejoe.sportsday.domain.auth.SportsDayUser;

public interface ISportsDayUserSupplier {
    SportsDayUser getUser();
}
