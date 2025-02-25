package com.ratracejoe.sportsday.actions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import net.serenitybdd.core.steps.UIInteractions;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import static net.serenitybdd.rest.SerenityRest.*;

public class SportsDayAuthActions extends UIInteractions {
    EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

    @Given("User connected to auth")
    public void givenUserConnectedToAuth() {
        var forEnv = EnvironmentSpecificConfiguration
                .from(variables);
        String baseUri = forEnv.getProperty("accounts.service.url");
        String username = forEnv.getProperty("service.username");
        String password = forEnv.getProperty("service.password");
        given().baseUri(baseUri)
                .basePath("/api/auth")
                .trustStore("../local/certs/sports-day.truststore.jks", "changeit")
                .accept(ContentType.JSON)
                .contentType(ContentType.URLENC)
                .formParam("username", username)
                .formParam("password", password);
    }

    @When("User logs in")
    public void whenUserLogsIn() {
        when().post("/login");
    }

    @Then("User receives token")
    public void thenUserReceivesToken() {
        then().statusCode(HttpStatus.SC_OK).body("accessToken", Matchers.isA(String.class));
    }
}
