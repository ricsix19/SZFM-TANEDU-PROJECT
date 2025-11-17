Feature: Messaging between users
  In order to notify students
  As a teacher
  I want to send messages to my students

  Scenario: Teacher sends message to a student
    Given a teacher "teacher@example.com" and a student "student@example.com" exist
    When the teacher sends a message "Exam postponed" to the student
    Then the student should receive the message and it should be persisted
