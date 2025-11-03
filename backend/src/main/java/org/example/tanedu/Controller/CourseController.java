package org.example.tanedu.Controller;

import org.example.tanedu.DTO.CourseDTO;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;



    @GetMapping("/getAll")
    public List<CourseDTO> getAllCourse(){
        return courseService.getAllCourse();
    }
    @GetMapping("/getById/{id}")
    public CourseDTO getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }
    @GetMapping("/getByCurrentTeacher")
    @PreAuthorize("hasRole('TEACHER')")
    public List<CourseDTO> getCoursesByCurrentTeacher() {
        return courseService.getCoursesByCurrentTeacher();
    }

    @GetMapping("/{courseId}/students")
    @PreAuthorize("hasAnyRole('TEACHER', 'SYSADMIN')")
    public ResponseEntity<?> getStudentsByCourse(@PathVariable Long courseId) {
        try {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            List<User> students = userRepository.findAllByDepartmentAndRole(
                    course.getDepartment(), Role.STUDENT);

            return ResponseEntity.ok(students.stream()
                    .map(s -> Map.of(
                            "id", s.getId(),
                            "fullName", s.getFullName()
                    ))
                    .toList());
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }
    @GetMapping("/getCourseByDepartmentNameForCurrentUser")
    public List<CourseDTO> getCourseByDepartmentNameForCurrentUser(){
        return courseService.getCourseByDepartmentNameForCurrentUser();
    }

    @GetMapping("/getCourseByDepartmentName")
    public List<CourseDTO> getCourseByDepartmentName(@RequestParam String name){
        return courseService.getCoursesByDepartmentName(name);
    }

    @PutMapping("/updateName/{id}")
    public Course updateCourseNameById(@PathVariable Long id, @RequestBody String name){
        return courseService.updateCourseNameById(id,name);
    }

    @PutMapping("/updateDuration/{id}")
    public Course updateCourseDurationById(@PathVariable Long id, @RequestBody String duration){
        return courseService.updateCourseDurationById(id,duration);
    }

    @PutMapping("/updateDay/{id}")
    public Course updateCourseDayById(@PathVariable Long id, @RequestBody String day){
        return courseService.updateCourseDayById(id,day);
    }

    @PostMapping("/create")
    public CourseDTO createCourse(@RequestBody Course course){
        return courseService.createCourse(course);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteCourseById(@PathVariable Long id){
       return courseService.deleteCourseById(id);
    }
}
