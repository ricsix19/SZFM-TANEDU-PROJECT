package org.example.tanedu.Repository;

import org.example.tanedu.Model.Grade;
import org.example.tanedu.Model.Subject;
import org.example.tanedu.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade,Long> {
    List<Grade> findGradesByStudent(User student);

    List<Grade> findGradesByStudentAndSubject(User student, Subject subject);

}
