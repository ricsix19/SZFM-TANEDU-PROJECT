Feature: Course and subject management
  As an admin
  I want to create and assign courses and teachers

  Scenario: Admin creates a course and assigns a teacher
    Given an admin user exists
    When the admin creates course "Math101" and assigns teacher "teacher@example.com"
    Then the course should exist and the teacher should be linked to the course
