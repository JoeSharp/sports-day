Feature: Monitoring
  Scenario: Any user can inquire of system health
    When jenny requests system health
    Then should receive HTTP status 200
    And the system reports health UP