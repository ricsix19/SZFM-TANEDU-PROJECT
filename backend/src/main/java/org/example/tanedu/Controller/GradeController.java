package org.example.tanedu.Controller;

import org.example.tanedu.DTO.GradeDTO;
import org.example.tanedu.Model.Grade;
import org.example.tanedu.Service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    @PostMapping("/create")
    public GradeDTO createGrade(@RequestBody Grade grade){
        return gradeService.createGrade(grade);
    }

    @GetMapping("/getAllByStudentId/{id}")
    public List<GradeDTO> getAllGradesByStudentId(@PathVariable Long id){
        return gradeService.getAllGradesByStudentId(id);
    }
    @GetMapping("/getAllGradesByStudentEmail/{email}")
    public List<GradeDTO> getAllGradesByStudentId(@PathVariable String email){
        return gradeService.getAllGradesByStudentEmail(email);
    }

    @GetMapping("/getAllByCurrentUser")
    public List<GradeDTO> getAllGradesByCurrentUser(){
        return gradeService.getAllGradesByCurrentUser();
    }

    @DeleteMapping("/deleteGradeById/{id}")
    public ResponseEntity<String> deleteGradeById(@PathVariable Long id) {
        gradeService.deleteGradeById(id);
        return ResponseEntity.ok("Grade deleted successfully");
    }
}
