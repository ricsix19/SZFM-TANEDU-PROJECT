package org.example.tanedu.DTO;


import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.Grade;


@Getter
@Setter
public class GradeDTO {
    private Long id;
    private Integer value;
    private String comment;
    private String studentName;
    private String teacherName;

    public GradeDTO(Grade grade){
        this.id = grade.getId();
        this.value = grade.getValue();
        this.comment = grade.getComment();
        this.studentName = grade.getStudent().getFullName();
        this.teacherName = grade.getTeacher().getFullName();
    }
}
