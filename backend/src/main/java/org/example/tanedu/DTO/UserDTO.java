// language: java
package org.example.tanedu.DTO;

import lombok.Getter;
import lombok.Setter;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.User;
import java.util.Date;

@Getter
@Setter
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Date birthDate;
    private String role;
    private String departmentName;
    private String fullName;
    private String subject;
    private String classLeaderOf;

    public UserDTO(User u) {
        this.id = u.getId();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.email = u.getEmail();
        this.birthDate = u.getBirthDate();
        this.role = u.getRole() != null ? u.getRole().name() : null;
        this.fullName = u.getFullName();
        this.subject = u.getSubject() != null ? u.getSubject().name() : null;
        Department dept = u.getDepartment();
        this.departmentName = dept != null ? dept.getName() : null;

        Department classLeaderDept = u.getClassLeaderOf();
        this.classLeaderOf = classLeaderDept != null ? classLeaderDept.getName() : null;
    }


}
