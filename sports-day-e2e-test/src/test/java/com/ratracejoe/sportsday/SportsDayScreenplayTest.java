package com.ratracejoe.sportsday;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

@ExtendWith(SerenityJUnit5Extension.class)
public class SportsDayScreenplayTest {
    private static String restApiBaseUrl;
    private static String username;
    private static String password;
    private static Actor joe;

    @BeforeAll
    public static void beforeAll() {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        var forEnv = EnvironmentSpecificConfiguration
                .from(variables);

        restApiBaseUrl = forEnv.getProperty("accounts.service.url");
        username = forEnv.getProperty("service.username");
        password = forEnv.getProperty("service.password");
        joe = Actor.named("Joe").whoCan(CallAnApi.at(restApiBaseUrl));
    }

    @Test
    public void checkHealth() {
        joe.attemptsTo(Get.resource("/api/actuator/health"));
        joe.should(seeThatResponse("Health is UP",
                r -> r.statusCode(200)
                        .body("status", Matchers.equalTo("UP"))));
    }

    @Test
    public void login() {
        joe.attemptsTo(Post.to("/api/auth/login")
                .with(r -> r
                        .accept(ContentType.JSON)
                        .contentType(ContentType.URLENC)
                        .formParam("username", username)
                        .formParam("password", password)));
        joe.should(seeThatResponse("Access Token Provided",
                r -> r
                        .statusCode(200)
                        .body("accessToken", Matchers.isA(String.class))));
    }
}
