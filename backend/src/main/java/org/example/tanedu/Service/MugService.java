package org.example.tanedu.Service;

import org.example.tanedu.Model.*;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.MessageRepository;
import org.example.tanedu.Repository.MugRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MugService {

    @Autowired
    private MugRepository mugSendRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private Utils utils;

    public Map<String, Boolean> getStatus(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        User current = userRepository.findByEmail(utils.getCurrentUserEmail());
        boolean sent = mugSendRepository.existsByCourseAndUser(course, current);
        return Map.of("userSent", sent);
    }

    public ResponseEntity<?> sendMug(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        User current = userRepository.findByEmail(utils.getCurrentUserEmail());
        if (current == null) return ResponseEntity.badRequest().body(Map.of("message", "Current user not found"));

        if (current.getRole() == Role.SYSADMIN || current.getRole() == Role.TEACHER) {
            return ResponseEntity.badRequest().body(Map.of("message", "Only students can send mugs"));
        }

        if (course.getDepartment() == null || current.getDepartment() == null ||
                !course.getDepartment().getId().equals(current.getDepartment().getId())) {
            return ResponseEntity.badRequest().body(Map.of("message", "Student must belong to the course department"));
        }

        if (mugSendRepository.existsByCourseAndUser(course, current)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Already sent"));
        }

        mugSendRepository.save(new Mug(current, course));

        List<User> students = userRepository.findAllByDepartmentAndRole(course.getDepartment(), Role.STUDENT);
        long totalStudents = students.size();
        long sentCount = mugSendRepository.countByCourse(course);

        if (course.getTeacher() != null && totalStudents > 0 && sentCount >= totalStudents) {
            String content = "Your class has received a mug from the group.";
            Message msg = new Message(null, course.getTeacher(), content);
            messageRepository.save(msg);
        }

        return ResponseEntity.ok(Map.of("message", "Mug sent"));
    }
}
