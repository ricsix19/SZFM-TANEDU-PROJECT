package org.example.tanedu.Model;

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
    @Column(nullable = false)
    private String department;
    private Date birthDate;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

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
}
