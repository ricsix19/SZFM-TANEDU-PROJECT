package org.example.tanedu.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "department")
    private List<Course> courseList;

    @OneToMany(mappedBy = "department")
    // @JsonIgnoreProperties({"department", "courseList", "receivedGrades", "givenGrades", "studentMessages", "teacherMessages"})
    private List<User> students;

    @OneToOne(mappedBy = "classLeaderOf")
    private User classLeader;
}
