package com.ratracejoe.sportsday.web.config;

import com.ratracejoe.sportsday.ports.incoming.auth.ISportsDayUserSupplier;
import com.ratracejoe.sportsday.rest.auth.OAuthSportsDayUserSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthAdapterConfig {
  @Bean
  public ISportsDayUserSupplier userSupplier() {
    return new OAuthSportsDayUserSupplier();
  }
}
