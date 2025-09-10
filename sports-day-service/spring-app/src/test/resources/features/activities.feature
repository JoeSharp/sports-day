Feature: Activities can be managed
  Background:
    Given user logged in as JOE

  Scenario: Client requests list of activities
    When the client requests activities
    And all audits have been received
    Then the client receives list response with a status code of 200
    And an audit captures the reading of the list of activities

  Scenario: Non existent activity fetch handled correctly
    Given random ID is generated
    When the client requests that activity by ID
    And all audits have been received
    Then the client receives single response with a status code of 404
    And an audit captures the failed read

  Scenario: Non existent activity deletion handled correctly
    Given random ID is generated
    When the client deletes that activity by ID
    And all audits have been received
    Then the client receives void response with a status code of 404
    And an audit captures the failed deletion

  Scenario: Client requests individual activity
    Given random activity created
    When the client requests that activity by ID
    And all audits have been received
    Then the client receives single response with a status code of 200
    And an audit captures the read of that activity

  Scenario: Client creates new activity
    When the client creates an activity Jogging with random description
    And all audits have been received
    Then the client receives single response with a status code of 201
    And an audit captures the creation of that activity

  Scenario: Client deletes an activity
    Given random activity created
    When the client deletes that activity by ID
    And all audits have been received
    Then the client receives void response with a status code of 200
    And the activity no longer exists
    And an audit captures the deletion of that activity
