package org.example.tanedu.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.User;

import java.util.List;

@Getter
@Setter
public class DepartmentDTO {
    private Long id;
    private String name;
    private List<Long> courseIds;
    private List<Long> studentIds;

    public DepartmentDTO(Department department){
        this.id =department.getId();
        this.name=department.getName();
        this.courseIds=department.getCourseList().stream().map(Course::getId).toList();
        this.studentIds=department.getStudents().stream().map(User::getId).toList();
    }

}
