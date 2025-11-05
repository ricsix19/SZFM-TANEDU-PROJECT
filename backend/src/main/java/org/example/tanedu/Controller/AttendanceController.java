package org.example.tanedu.Controller;

import org.example.tanedu.DTO.AttendanceRequestDTO;
import org.example.tanedu.DTO.AttendanceResponseDTO;
import org.example.tanedu.Model.Attendance;

import org.example.tanedu.Service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;


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

    @GetMapping("/my-attendances")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<?> getMyAttendances() {
        return attendanceService.getMyAttendances();
    }

    @DeleteMapping("/{attendanceId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'SYSADMIN')")
    public ResponseEntity<?> deleteAttendance(@PathVariable Long attendanceId) {
        return attendanceService.deleteAttendance(attendanceId);
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'STUDENT')")
    public ResponseEntity<?> getAttendance(@PathVariable Long courseId,
                                           @RequestParam String date,
                                           @RequestParam(required = false) String timeSlot) {
        try {
            LocalDate localDate = LocalDate.parse(date);
            List<Attendance> attendances = attendanceService.getAttendanceByCourseAndDate(courseId, localDate, timeSlot);

            List<AttendanceResponseDTO> dtoList = attendances.stream()
                    .map(a -> new AttendanceResponseDTO(
                            a.getId(),
                            a.getDate(),
                            a.getPresent(),
                            a.getStudent() != null ? a.getStudent().getId() : null,
                            a.getStudent() != null ? a.getStudent().getFullName() : null,
                            a.getCourse() != null ? a.getCourse().getId() : null,
                            a.getCourse() != null ? a.getCourse().getName() : null,
                            a.getTimeSlot()
                    ))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtoList);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

}
