package org.example.tanedu.Controller;

import org.example.tanedu.DTO.DepartmentDTO;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/create")
    public DepartmentDTO createDepartment(@RequestBody Department department){
        return departmentService.createDepartment(department);
    }
    @GetMapping("/getAll")
    public List<DepartmentDTO> getAllDepartments(){
        return departmentService.getAllDepartments();
    }
    @GetMapping("/getDepartmentClassLeader/{name}")
    public String getDepartmentClassLeader(@PathVariable String name){
        return departmentService.getDepartmentClassLeader(name);
    }

    @GetMapping("/getById/{id}")
    public DepartmentDTO getAllDepartments(@PathVariable Long id){
        return departmentService.getDepartmentById(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public String deleteDepartmentById(@PathVariable Long id){
        return departmentService.deleteDepartmentById(id);
    }

    @PutMapping("/updateById/{id}")
    public String updateDepartmentById(@PathVariable Long id, @RequestBody String name){
        return departmentService.updateDepartmentById(id, name);
    }


}
