package org.example.tanedu.Service;

import org.example.tanedu.DTO.CourseDTO;
import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.DepartmentRepository;
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
    private DepartmentRepository departmentRepository;
    @Autowired
    private Utils utils;

    public CourseDTO createCourse(Course course) {
        Course courseToAdd = new Course();
        courseToAdd.setName(course.getName());
        courseToAdd.setDay(course.getDay());
        courseToAdd.setDuration(course.getDuration());

        if (course.getTeacher() != null && course.getTeacher().getId() != null) {
            User teacher = userRepository.findById(course.getTeacher().getId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found!"));
            if ("STUDENT".equals(teacher.getRole().name())) {
                throw new RuntimeException("You can't set student as teacher");
            }
            courseToAdd.setTeacher(teacher);
        } else if (course.getTeacher() != null && course.getTeacher().getFirstName() != null) {
            User teacher = userRepository.findByFirstNameContainingIgnoreCase(course.getTeacher().getFirstName());
            if (teacher == null) throw new RuntimeException("Teacher not found");
            if ("STUDENT".equals(teacher.getRole().name()))
                throw new RuntimeException("You can't set student as teacher");
            courseToAdd.setTeacher(teacher);
        } else if (course.getTeacher() != null && course.getTeacher().getEmail() != null) {
            User teacher = userRepository.findByEmail(course.getTeacher().getEmail());
            if (teacher == null) throw new RuntimeException("Teacher not found");
            if ("STUDENT".equals(teacher.getRole().name()))
                throw new RuntimeException("You can't set student as teacher");
            courseToAdd.setTeacher(teacher);
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
                    throw new RuntimeException("Department already has a course at this time");
                }
            }
        }

        Course saved = courseRepository.save(courseToAdd);
        return new CourseDTO(saved);
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
