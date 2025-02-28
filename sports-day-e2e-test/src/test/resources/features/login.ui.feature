Feature: Authorisation via UI

  Scenario: User logs in to UI
    Given joe can login as service user
    When they open the UI
    And they type in and submit their credentials
    Then activities table is present
