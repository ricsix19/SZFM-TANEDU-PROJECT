package org.example.tanedu.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponseDTO {
    private Long id;
    private LocalDate date;
    private Boolean present;
    private Long studentId;
    private String studentName;
    private Long courseId;
    private String courseName;
    private String timeSlot;
}
