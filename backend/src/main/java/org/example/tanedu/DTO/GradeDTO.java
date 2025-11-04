package org.example.tanedu.DTO;


import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.Grade;
import org.example.tanedu.Model.Subject;

import java.time.LocalDateTime;


@Getter
@Setter
public class GradeDTO {
    private Long id;
    private Integer value;
    private Subject subject;

    private LocalDateTime createdAt;
    private String studentName;
    private String teacherName;

    public GradeDTO(Grade grade){
        this.id = grade.getId();
        this.value = grade.getValue();
        this.subject = grade.getSubject();
        this.createdAt = grade.getCreatedAt();
        this.studentName = grade.getStudent().getFullName();
        this.teacherName = grade.getTeacher().getFullName();
    }
}
