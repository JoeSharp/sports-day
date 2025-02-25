package com.ratracejoe.sportsday.tasks;

import lombok.RequiredArgsConstructor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

@RequiredArgsConstructor
public class GetActivities implements Task {

    @Override
    public <T extends Actor> void performAs(T t) {
        String accessToken = t.recall(LoginAsUser.KEY_ACCESS_TOKEN);
        t.attemptsTo(Get.resource("/api/activities")
                .with(r -> r
                        .header("Authorization", "Bearer " + accessToken)));
    }
}
