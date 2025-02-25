package com.ratracejoe.sportsday.tasks;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.model.LoginResponseDTO;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;

import static com.ratracejoe.sportsday.tasks.LoginAsUser.KEY_ACCESS_TOKEN;
import static com.ratracejoe.sportsday.tasks.LoginAsUser.KEY_REFRESH_TOKEN;

public class RefreshAsUser implements Task {

    @Override
    public <T extends Actor> void performAs(T t) {
        String accessToken = t.recall(KEY_ACCESS_TOKEN);
        String refreshToken = t.recall(KEY_REFRESH_TOKEN);

        t.attemptsTo(Post.to("/api/auth/refresh")
                .with(r -> r
                        .accept(ContentType.JSON)
                        .contentType(ContentType.URLENC)
                        .header("Authorization", "Bearer " + accessToken)
                        .formParam("refreshToken", refreshToken)));
        LoginResponseDTO loginResponse = SerenityRest.lastResponse()
                .jsonPath()
                .getObject(".", LoginResponseDTO.class);
        t.remember(KEY_ACCESS_TOKEN, loginResponse.accessToken());
        t.remember(KEY_REFRESH_TOKEN, loginResponse.refreshToken());
    }
}
