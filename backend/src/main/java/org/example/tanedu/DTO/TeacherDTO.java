package org.example.tanedu.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.*;

import java.util.List;

@Getter
@Setter
public class TeacherDTO {
    private String lastName;
    private String firstName;
    private String email;
    private Role role;
    private List<Long> courseList;
    private List<Integer> gradeList;
    private String department;

    public TeacherDTO(User user){
        this.lastName = user.getLastName();
        this.firstName = user.getFirstName();
        this.email = user.getEmail();
        this.role = user.getRole();
        this.courseList = user.getCourseList().stream().map(Course::getId).toList();
        this.gradeList = user.getGivenGrades().stream().map(Grade::getValue).toList();
        this.department = user.getDepartment().getName();
    }
}
