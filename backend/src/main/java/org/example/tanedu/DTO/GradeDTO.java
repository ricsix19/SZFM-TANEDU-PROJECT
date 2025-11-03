package org.example.tanedu.DTO;


import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.Grade;

import java.time.LocalDateTime;


@Getter
@Setter
public class GradeDTO {
    private Long id;
    private Integer value;
    private String comment;
    private LocalDateTime createdAt;
    private String studentName;
    private String teacherName;

    public GradeDTO(Grade grade){
        this.id = grade.getId();
        this.value = grade.getValue();
        this.comment = grade.getComment();
        this.createdAt = grade.getCreatedAt();
        this.studentName = grade.getStudent().getFullName();
        this.teacherName = grade.getTeacher().getFullName();
    }
}
