package org.example.tanedu.Service;

import org.example.tanedu.DTO.AttendanceRequestDTO;
import org.example.tanedu.Model.Attendance;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.AttendanceRepository;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void saveAttendance(AttendanceRequestDTO request) {
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        for (AttendanceRequestDTO.StudentPresence sp : request.getStudents()) {
            User student = userRepository.findById(sp.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            Attendance attendance = attendanceRepository
                    .findByStudentIdAndCourseIdAndDate(sp.getStudentId(), request.getCourseId(), request.getDate())
                    .orElse(new Attendance());

            attendance.setStudent(student);
            attendance.setCourse(course);
            attendance.setDate(request.getDate());
            attendance.setPresent(sp.getPresent());

            attendanceRepository.save(attendance);
        }
    }

    public List<Attendance> getAttendanceByCourseAndDate(Long courseId, LocalDate date) {
        return attendanceRepository.findByCourseIdAndDate(courseId, date);
    }
}
