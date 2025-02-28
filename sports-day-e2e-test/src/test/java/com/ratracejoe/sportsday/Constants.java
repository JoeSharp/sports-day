package com.ratracejoe.sportsday;

import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

public interface Constants {
  EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
  EnvironmentSpecificConfiguration forEnv = EnvironmentSpecificConfiguration.from(variables);
  String UI_BASE_URL = forEnv.getProperty("ui.url");
  String REST_API_BASE_URL = forEnv.getProperty("service.url");
  String SERVICE_USERNAME = forEnv.getProperty("service.username");
  String SERVICE_PASSWORD = forEnv.getProperty("service.password");

  // Keys of facts that can be remembered & recalled
  String KEY_ACCESS_TOKEN = "accessToken";
  String KEY_REFRESH_TOKEN = "refreshToken";
  String KEY_CREATED_ACTIVITY_ID = "CreatedActivityId";
  String KEY_CREATED_ACTIVITY = "CreatedActivity";
  String KEY_DELETED_ACTIVITY_ID = "DeletedActivityId";

  // UI constants
  String ID_TABLE_ACTIVITIES = "activities";
}
