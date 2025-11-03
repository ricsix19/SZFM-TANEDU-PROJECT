package org.example.tanedu.Controller;

import org.example.tanedu.DTO.AttendanceRequestDTO;
import org.example.tanedu.DTO.CourseDTO;
import org.example.tanedu.Model.Attendance;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Service.AttendanceService;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private Utils utils;

    @PostMapping
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<?> saveAttendance(@RequestBody AttendanceRequestDTO request) {
        try {
            attendanceService.saveAttendance(request);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Attendance saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public ResponseEntity<?> getAttendance(
            @PathVariable Long courseId,
            @RequestParam String date) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Attendance> attendances = attendanceService.getAttendanceByCourseAndDate(courseId, localDate);
            return ResponseEntity.ok(attendances);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
