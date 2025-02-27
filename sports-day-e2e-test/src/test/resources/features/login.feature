Feature: Authorisation

  Scenario: User logs in
    Given serviceUser can login
    When user types in their credentials
    Then activities table is present
