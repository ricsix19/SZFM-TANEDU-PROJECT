package org.example.tanedu.Service;

import org.example.tanedu.DTO.CourseDTO;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.Subject;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.DepartmentRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private Utils utils;

    public ResponseEntity<?> createCourse(Course course) {
        try {
            Course courseToAdd = new Course();
            courseToAdd.setName(course.getName());
            courseToAdd.setDay(course.getDay());
            courseToAdd.setDuration(course.getDuration());

            if (course.getTeacher() != null && course.getTeacher().getId() != null) {
                Long tid = course.getTeacher().getId();
                User teacher = userRepository.findById(tid).orElse(null);
                if (teacher == null) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Teacher not found with id: " + tid));
                }
                if ("STUDENT".equals(teacher.getRole().name())) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Selected user is a student and cannot be assigned as a teacher"));
                }
                courseToAdd.setTeacher(teacher);
            } else if (course.getTeacher() != null && course.getTeacher().getFirstName() != null) {
                String fname = course.getTeacher().getFirstName();
                User teacher = userRepository.findByFirstNameContainingIgnoreCase(fname);
                if (teacher == null) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Teacher not found with first name: " + fname));
                }
                if ("STUDENT".equals(teacher.getRole().name())) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Selected user is a student and cannot be assigned as a teacher"));
                }
                courseToAdd.setTeacher(teacher);
            } else if (course.getTeacher() != null && course.getTeacher().getEmail() != null) {
                String email = course.getTeacher().getEmail();
                User teacher = userRepository.findByEmail(email);
                if (teacher == null) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Teacher not found with email: " + email));
                }
                if ("STUDENT".equals(teacher.getRole().name())) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Selected user is a student and cannot be assigned as a teacher"));
                }
                courseToAdd.setTeacher(teacher);
            }

            if (courseToAdd.getTeacher() != null && courseToAdd.getName() != null) {
                org.example.tanedu.Model.Subject courseSubject;
                try {
                    courseSubject = Subject.fromString(courseToAdd.getName());
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Invalid subject name: " + courseToAdd.getName()));
                }

                if (courseToAdd.getTeacher().getSubject() == null ||
                        !courseToAdd.getTeacher().getSubject().equals(courseSubject)) {
                    return ResponseEntity.badRequest().body(Map.of("message", "Selected teacher does not teach the subject: " + courseToAdd.getName()));
                }
            }

            if (courseToAdd.getTeacher() != null) {
                List<Course> teacherCourses = courseRepository.findByTeacherId(courseToAdd.getTeacher().getId());
                for (Course existing : teacherCourses) {
                    if (existing.getDay() != null && existing.getDay().equals(courseToAdd.getDay())) {
                        if (utils.isOverlapping(existing.getDuration(), courseToAdd.getDuration())) {
                            return ResponseEntity.badRequest().body(Map.of("message",
                                    "Selected teacher already has a course on " + courseToAdd.getDay() + " at " + courseToAdd.getDuration()));
                        }
                    }
                }
            }

            if (course.getDepartment() != null && course.getDepartment().getName() != null) {
                Department department = departmentRepository.findByName(course.getDepartment().getName());
                if (department == null) {
                    Department newDept = new Department();
                    newDept.setName(course.getDepartment().getName());
                    department = departmentRepository.save(newDept);
                }
                courseToAdd.setDepartment(department);

                List<Course> sameDayCourses = courseRepository.findAllByDepartment_NameAndDay(department.getName(), courseToAdd.getDay());
                for (Course existing : sameDayCourses) {
                    if (utils.isOverlapping(existing.getDuration(), courseToAdd.getDuration())) {
                        return ResponseEntity.badRequest().body(Map.of("message",
                                "Department '" + department.getName() + "' already has a course on " + courseToAdd.getDay() + " at " + courseToAdd.getDuration()));
                    }
                }
            }

            Course saved = courseRepository.save(courseToAdd);
            return ResponseEntity.ok(new CourseDTO(saved));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "An unexpected error occurred"));
        }
    }

    public List<CourseDTO> getAllCourse(){
        return courseRepository.findAll().stream().map(CourseDTO::new).toList();
    }

    public List<CourseDTO> getCourseByDepartmentNameForCurrentUser(){
        User foundUser = userRepository.findByEmail(utils.getCurrentUserEmail());

        return courseRepository.findAllByDepartment_Name(foundUser.getDepartment().getName()).stream().map(CourseDTO::new).toList();
    }

    public List<CourseDTO> getCoursesByDepartmentName(String name){
        List<Course> foundCourses =  courseRepository.findAllByDepartment_Name(name);
        return foundCourses.stream().map(CourseDTO::new).toList();
    }

    public List<CourseDTO> getCoursesByCurrentTeacher() {
        User teacher = userRepository.findByEmail(utils.getCurrentUserEmail());
        return courseRepository.findByTeacherId(teacher.getId())
                .stream()
                .map(CourseDTO::new)
                .toList();
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
