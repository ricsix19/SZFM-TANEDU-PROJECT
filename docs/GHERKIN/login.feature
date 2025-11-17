Feature: User authentication
  As a registered user
  I want to log in with email and password
  So that I can access protected resources

  Scenario: Successful login returns JWT
    Given a user exists with email "test@student.edu" and password "P@ssw0rd"
    When the user requests authentication with those credentials
    Then the response should contain a valid JWT token
