package com.ratracejoe.sportsday.abilities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Ability;
import net.serenitybdd.screenplay.Actor;

@RequiredArgsConstructor
@Getter
public class Authenticate implements Ability {
    private final String username;
    private final String password;

    public static Authenticate withCredentials(String username, String password) {
        return new Authenticate(username, password);
    }

    public static Authenticate as(Actor actor) {
        return actor.abilityTo(Authenticate.class);
    }
}
