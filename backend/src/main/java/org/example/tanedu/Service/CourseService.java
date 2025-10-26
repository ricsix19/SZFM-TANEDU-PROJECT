package org.example.tanedu.Service;

import org.example.tanedu.DTO.CourseDTO;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;

    public Course createCourse(Course course){
        return courseRepository.save(course);
    }

    public List<CourseDTO> getAllCourse(){
        return courseRepository.findAll().stream().map(CourseDTO::new).toList();
    }

    public List<CourseDTO> getCourseByDepartmentName(){
        User foundUser = userRepository.findByEmail(utils.getCurrentUserEmail());

        return courseRepository.findAllByDepartment_Name(foundUser.getDepartment().getName()).stream().map(CourseDTO::new).toList();
    }

    public CourseDTO getCourseById(Long id){
        Course foundCourse = courseRepository.findById(id).orElseThrow(()->new RuntimeException("No course found with id: "+id));
        return new CourseDTO(foundCourse);
    }
    public Course updateCourseNameById(Long id, String name){
        Course foundCourse = courseRepository.findById(id).orElseThrow(()->new RuntimeException("No course found with id: "+id));

        if(name != null) foundCourse.setName(name);

        return courseRepository.save(foundCourse);
    }
    public Course updateCourseDayById(Long id, String day){
        Course foundCourse = courseRepository.findById(id).orElseThrow(()->new RuntimeException("No course found with id: "+id));

        if(day != null) foundCourse.setDay(day);

        return courseRepository.save(foundCourse);
    }

    public Course updateCourseDurationById(Long id, String duration){
        Course foundCourse = courseRepository.findById(id).orElseThrow(()->new RuntimeException("No course found with id: "+id));

        if(duration != null) foundCourse.setDay(duration);

        return courseRepository.save(foundCourse);
    }

    public String deleteCourseById(Long id){
        Course foundCourse = courseRepository.findById(id).orElseThrow(()->new RuntimeException("No course found with id: "+id));

        courseRepository.delete(foundCourse);
        return "Course deleted!";
    }
}
