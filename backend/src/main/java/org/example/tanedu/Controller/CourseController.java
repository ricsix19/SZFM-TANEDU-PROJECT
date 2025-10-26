package org.example.tanedu.Controller;

import org.example.tanedu.DTO.CourseDTO;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/getAll")
    public List<CourseDTO> getAllCourse(){
        return courseService.getAllCourse();
    }
    @GetMapping("/getById/{id}")
    public CourseDTO getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }
    @GetMapping("/getCourseByDepartmentName")
    public List<CourseDTO> getCourseByDepartmentName(){
        return courseService.getCourseByDepartmentName();
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
