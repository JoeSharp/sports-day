Feature: Authorisation via API

  Scenario: A service user logs in to API
    Given joe can login as service user
    When they call login with their credentials
    Then should receive HTTP status 200
    And they should be provided with an access token

  Scenario: A logged in user, can refresh their token
    Given joe successfully logs into API
    When they call refresh with their token
    Then should receive HTTP status 200
    And they should be provided with an access token

  Scenario: A logged in user, can log back out
    Given joe successfully logs into API
    When they call logout
