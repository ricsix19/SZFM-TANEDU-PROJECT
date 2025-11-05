package org.example.tanedu.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AttendanceRequestDTO {
    private Long courseId;
    private LocalDate date;
    private String timeSlot;
    private List<StudentPresence> students;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class StudentPresence {
        private Long studentId;
        private Boolean present;
    }


}
