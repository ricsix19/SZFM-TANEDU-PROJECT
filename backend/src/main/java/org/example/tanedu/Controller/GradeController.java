package org.example.tanedu.Controller;

import org.example.tanedu.DTO.GradeDTO;
import org.example.tanedu.Model.Grade;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.GradeRepository;
import org.example.tanedu.Service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
