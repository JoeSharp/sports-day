package com.ratracejoe.sportsday;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public interface Constants {
  EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
  EnvironmentSpecificConfiguration forEnv = EnvironmentSpecificConfiguration.from(variables);
  String REST_API_BASE_URL = forEnv.getProperty("service.url");
  String SERVICE_USERNAME = forEnv.getProperty("service.username");
  String SERVICE_PASSWORD = forEnv.getProperty("service.password");
}
