package org.example.tanedu.Repository;

import org.example.tanedu.Model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade,Long> {
}
