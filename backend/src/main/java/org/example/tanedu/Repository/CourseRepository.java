package org.example.tanedu.Repository;

import org.example.tanedu.Model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findAllByDepartment_Name(String departmentName);

    List<Course> findAllByDepartment_NameAndDay(String name, String day);

    List<Course> findByTeacherId(Long id);
}
