Feature: Attendance management
  As an instructor
  I want to record attendance for students
  So that attendance is tracked per lesson

  Scenario: Mark attendance for a student
    Given a class session exists for course "Math101" on "2025-11-20"
    When the instructor marks student "student@example.com" as present
    Then the attendance record for that session should show the student as present
