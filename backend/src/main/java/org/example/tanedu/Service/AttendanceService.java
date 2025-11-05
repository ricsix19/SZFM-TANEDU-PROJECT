package org.example.tanedu.Service;

import org.example.tanedu.DTO.AttendanceRequestDTO;
import org.example.tanedu.Model.Attendance;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.AttendanceRepository;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;

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

    public ResponseEntity<?> getMyAttendances() {
        try {
            User currentUser = userRepository.findByEmail(utils.getCurrentUserEmail());
            if (currentUser.getRole() != Role.STUDENT) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Only students can view their attendances");
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            }

            List<Attendance> attendances = attendanceRepository.findByStudentId(currentUser.getId());

            // Filter only absences (present = false) and map to a response DTO
            List<Map<String, Object>> absences = attendances.stream()
                    .filter(attendance -> !attendance.getPresent())
                    .map(attendance -> {
                        Map<String, Object> absenceInfo = new HashMap<>();
                        absenceInfo.put("id", attendance.getId());
                        absenceInfo.put("date", attendance.getDate());
                        absenceInfo.put("courseName", attendance.getCourse().getName());
                        absenceInfo.put("courseId", attendance.getCourse().getId());
                        return absenceInfo;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(absences);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    public List<Attendance> getAttendanceByCourseAndDate(Long courseId, LocalDate date) {
        return attendanceRepository.findByCourseIdAndDate(courseId, date);
    }

    public ResponseEntity<?> deleteAttendance(Long attendanceId) {
        try {
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new RuntimeException("Attendance not found"));

            attendanceRepository.delete(attendance);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Attendance deleted successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}
