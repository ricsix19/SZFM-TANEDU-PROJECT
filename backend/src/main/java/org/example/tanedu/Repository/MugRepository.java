package org.example.tanedu.Repository;

import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Mug;
import org.example.tanedu.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MugRepository extends JpaRepository<Mug, Long> {
    boolean existsByCourseAndUser(Course course, User user);
    long countByCourse(Course course);
}
