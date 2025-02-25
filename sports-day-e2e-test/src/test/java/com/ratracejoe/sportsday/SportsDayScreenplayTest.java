package com.ratracejoe.sportsday;

import com.ratracejoe.sportsday.abilities.Authenticate;
import com.ratracejoe.sportsday.tasks.GetActivities;
import com.ratracejoe.sportsday.tasks.LoginAsUser;
import com.ratracejoe.sportsday.tasks.RefreshAsUser;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

@ExtendWith(SerenityJUnit5Extension.class)
public class SportsDayScreenplayTest {
    private Actor serviceMonitor;
    private Actor serviceUser;

    @BeforeEach
    public void beforeEach() {
        EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
        var forEnv = EnvironmentSpecificConfiguration
                .from(variables);

        String restApiBaseUrl = forEnv.getProperty("accounts.service.url");
        String username = forEnv.getProperty("service.username");
        String password = forEnv.getProperty("service.password");
        serviceUser = Actor.named("MrSharp")
                .whoCan(CallAnApi.at(restApiBaseUrl))
                .whoCan(Authenticate.withCredentials(username, password));

        serviceMonitor = Actor.named("MrDavies")
                .whoCan(CallAnApi.at(restApiBaseUrl));
    }

    @Test
    public void checkHealth() {
        serviceMonitor.attemptsTo(Get.resource("/api/actuator/health"));
        serviceMonitor.should(seeThatResponse("Health is UP",
                r -> r.statusCode(200)
                        .body("status", Matchers.equalTo("UP"))));
    }

    @Test
    public void login() {
        serviceUser.attemptsTo(new LoginAsUser());
        serviceUser.should(seeThatResponse("Access Token Provided",
                r -> r
                        .statusCode(200)
                        .body("accessToken", Matchers.isA(String.class))));
    }

    @Test
    public void refresh() {
        serviceUser.attemptsTo(new LoginAsUser());
        serviceUser.attemptsTo(new RefreshAsUser());
        serviceUser.should(seeThatResponse("Access Token Provided",
                r -> r
                        .statusCode(200)
                        .body("accessToken", Matchers.isA(String.class))));
    }

    @Test
    public void getActivitiesDenied() {
        serviceMonitor.attemptsTo(new GetActivities());
        serviceMonitor.should(seeThatResponse("Access Denied",
                r -> r.statusCode(401)));
    }

    @Test
    public void getActivities() {
        serviceUser.attemptsTo(new LoginAsUser());
        serviceUser.attemptsTo(new GetActivities());
        serviceUser.should(seeThatResponse("Activities Retrieved",
                r -> r.statusCode(200)));
    }
}
