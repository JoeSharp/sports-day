Feature: Manage Activities via UI
  Background:
    Given steve can login as service user
    And they open the UI
    When they type in and submit their credentials
    Then activities table is present

  Scenario: User creates activity
    Given they generate a random activity
    When they create the activity
    Then the activity is shown in the table

  Scenario: User deletes an activity
    Given they generate a random activity
    And they create the activity
    When they delete that activity
    Then the activity is removed from the table
