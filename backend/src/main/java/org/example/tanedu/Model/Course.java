package org.example.tanedu.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String day;
    private String duration;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
