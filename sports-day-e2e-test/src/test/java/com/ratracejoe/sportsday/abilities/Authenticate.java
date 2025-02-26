package com.ratracejoe.sportsday.abilities;

import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

public record Authenticate(String username, String password) implements Ability {
  public static Authenticate withCredentials(String username, String password) {
    return new Authenticate(username, password);
  }

  public static Authenticate as(Actor actor) {
    return actor.abilityTo(Authenticate.class);
  }
}
