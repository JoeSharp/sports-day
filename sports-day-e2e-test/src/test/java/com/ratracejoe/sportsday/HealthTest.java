package com.ratracejoe.sportsday;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@DisplayName("Application Monitoring")
public class HealthTest {
  private Actor serviceMonitor;

  @BeforeEach
  public void beforeEach() {
    EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
    var forEnv = EnvironmentSpecificConfiguration.from(variables);

    String restApiBaseUrl = forEnv.getProperty("accounts.service.url");

    serviceMonitor = Actor.named("MrDavies").whoCan(CallAnApi.at(restApiBaseUrl));
  }

  @Test
  @DisplayName("Any user can call the health endpoint")
  public void checkHealth() {
    serviceMonitor.attemptsTo(Get.resource("/api/actuator/health"));
    serviceMonitor.should(
        seeThatResponse(
            "Health is UP",
            r -> r.statusCode(HttpStatus.SC_OK).body("status", Matchers.equalTo("UP"))));
  }
}
