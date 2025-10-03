package org.example.tanedu.Service;

import org.example.tanedu.Model.Course;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.CourseRepository;
import org.example.tanedu.Repository.GradeRepository;
import org.example.tanedu.Repository.MessageRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private Utils utils;



}
