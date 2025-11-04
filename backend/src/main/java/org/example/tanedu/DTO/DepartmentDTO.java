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
    private List<CourseDTO> courses;
    private List<UserDTO> students;
    private UserDTO classLeader;

    public DepartmentDTO(Department department) {
        this.id = department.getId();
        this.name = department.getName();

        this.courses = department.getCourseList() != null
                ? department.getCourseList().stream().map(CourseDTO::new).toList()
                : List.of();

        this.students = department.getStudents() != null
                ? department.getStudents().stream().map(UserDTO::new).toList()
                : List.of();

        this.classLeader = department.getClassLeader() != null
                ? new UserDTO(department.getClassLeader())
                : null;
    }
}

