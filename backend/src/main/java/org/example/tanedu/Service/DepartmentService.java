package org.example.tanedu.Service;

import org.example.tanedu.DTO.DepartmentDTO;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public List<DepartmentDTO> getAllDepartments(){
        return departmentRepository.findAll().stream().map(DepartmentDTO::new).toList();
    }

    public DepartmentDTO getDepartmentById(Long id){
        Department foundDepartment = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("No department found with id: "+id));

        return new DepartmentDTO(foundDepartment);
    }

    public Department createDepartment(Department department){
        return departmentRepository.save(department);
    }

    public String deleteDepartmentById(Long id){
        Department foundDepartment = departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("No department found"));

        departmentRepository.delete(foundDepartment);

        return "Deleted";
    }

    public String updateDepartmentById(Long id, String name){
        Department foundDepartment = departmentRepository.findById(id).orElseThrow(()-> new RuntimeException("No department found"));

        if (name != null) {
            foundDepartment.setName(name);
        }
        departmentRepository.save(foundDepartment);

        return "Deleted";
    }
}
