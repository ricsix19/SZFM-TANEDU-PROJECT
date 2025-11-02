package org.example.tanedu.Service;

import org.example.tanedu.DTO.GradeDTO;
import org.example.tanedu.Model.Grade;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.GradeRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Utils utils;

    public GradeDTO createGrade(Grade grade){
        Grade gradeToAdd = new Grade();
        User foundStudent = userRepository.findByEmail(grade.getStudent().getEmail());
        gradeToAdd.setComment(grade.getComment());
        gradeToAdd.setStudent(foundStudent);
        if (grade.getTeacher() == null) {
            User foundUser = userRepository.findByEmail(utils.getCurrentUserEmail());
            gradeToAdd.setTeacher(foundUser);
        } else {
            gradeToAdd.setTeacher(grade.getTeacher());
        }
        gradeToAdd.setValue(grade.getValue());

        Grade savedGrade = gradeRepository.save(gradeToAdd);
        return new GradeDTO(savedGrade);
    }

    public List<GradeDTO> getAllGradesByStudentId(Long id){
        User foundUser = userRepository.findById(id).orElseThrow(()->new RuntimeException("No user found with id: "+id));
        return gradeRepository.findGradesByStudent(foundUser).stream().map(GradeDTO::new).toList();
    }

    public List<GradeDTO> getAllGradesByCurrentUser(){
        User foundUser = userRepository.findByEmail(utils.getCurrentUserEmail());
        return gradeRepository.findGradesByStudent(foundUser).stream().map(GradeDTO::new).toList();
    }
}
