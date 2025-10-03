package org.example.tanedu.DTO;
import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.Course;


@Getter
@Setter
public class CourseDTO {
    private Long id;
    private String name;
    private String day;
    private String duration;
    private String teacherName;
    private String departmentName;

    public CourseDTO (Course course){
        this.id = course.getId();
        this.name = course.getName();
        this.day = course.getDay();
        this.duration = course.getDuration();
        this.teacherName = course.getTeacher() != null ? course.getTeacher().getFullName() : "No teacher assigned";
        this.departmentName = course.getDepartment() != null ? course.getDepartment().getName()
                : "No department assigned";
    }


}
