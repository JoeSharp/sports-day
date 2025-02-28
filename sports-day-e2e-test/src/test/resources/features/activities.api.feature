Feature: Manage Activities via API
  Background:
    Given steve can login as service user
    When they call login with their credentials
    Then they should be provided with an access token

  Scenario: Retrieve the list of activities
    When they GET the list of activities
    Then should receive HTTP status 200
    Then the activities were retrieved

  Scenario: A user that hasn't logged in is denied access
    When User bob GET the list of activities
    Then should receive HTTP status 401

  Scenario: Create a new activity
    Given they generate a random activity
    When they POST that activity
    Then should receive HTTP status 201

  Scenario: Create a new activity and it appears in list
    Given a random activity is created
    When they GET the list of activities
    Then the new activity ID appears in that list

  Scenario: Create a new activity and retrieve it
    Given a random activity is created
    When they GET the created activity
    Then the new activity ID is retrieved

  Scenario: Delete an activity
    Given a random activity is created
    When they DELETE that activity
    And should receive HTTP status 200
    When they GET the list of activities
    Then the deleted activity ID does not appear in that list
