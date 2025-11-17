Feature: File upload
  In order to submit assignments
  As a student
  I want to upload files

  Scenario: Student uploads an assignment
    Given the student "student@example.com" is authenticated
    When the student uploads a file "assignment1.pdf" to course "Math101"
    Then the server should return a URL and the file should be accessible
