package org.example.tanedu.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lastName;
    private String firstName;
    @Column(unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private Date birthDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Subject subject;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courseList;

    @OneToMany(mappedBy = "student")
    private List<Grade> receivedGrades;

    @OneToMany(mappedBy = "teacher")
    private List<Grade> givenGrades;

    @OneToMany(mappedBy = "sender")
    private List<Message> studentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<Message> teacherMessages;

    @ManyToOne
    @JoinColumn(name = "department_id")
    @JsonIgnoreProperties({"students", "courseList"})
    private Department department;

    @OneToOne
    @JoinColumn(name = "class_leader_of_department_id")
    private Department classLeaderOf;

    public String getFullName(){
        return this.lastName +" "+ this.firstName;
    }
}
