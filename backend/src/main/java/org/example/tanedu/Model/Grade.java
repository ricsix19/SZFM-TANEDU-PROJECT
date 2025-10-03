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
@Table(name="grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer value;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "graded_student_id")
    private User student;

    @ManyToOne
    @JoinColumn(name = "grader_id")
    private User teacher;
}
