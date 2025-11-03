package org.example.tanedu.DTO;

import java.time.LocalDate;
import java.util.List;

public class AttendanceRequestDTO {
    private Long courseId;
    private LocalDate date;
    private List<StudentPresence> students;

    public static class StudentPresence {
        private Long studentId;
        private Boolean present;

        public Long getStudentId() { return studentId; }
        public void setStudentId(Long studentId) { this.studentId = studentId; }

        public Boolean getPresent() { return present; }
        public void setPresent(Boolean present) { this.present = present; }
    }

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public List<StudentPresence> getStudents() { return students; }
    public void setStudents(List<StudentPresence> students) { this.students = students; }
}
