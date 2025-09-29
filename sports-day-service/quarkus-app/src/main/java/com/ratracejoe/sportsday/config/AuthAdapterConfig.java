package com.ratracejoe.sportsday.config;

import com.ratracejoe.sportsday.auth.MemorySportsDayUserSupplier;
import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class AuthAdapterConfig {
  @Produces
  public ISportsDayUserSupplier userSupplier() {
    return new MemorySportsDayUserSupplier();
  }
}
