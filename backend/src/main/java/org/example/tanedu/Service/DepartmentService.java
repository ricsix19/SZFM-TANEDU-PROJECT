package org.example.tanedu.Service;

import org.example.tanedu.DTO.DepartmentDTO;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.DepartmentRepository;
import org.example.tanedu.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;

    public List<DepartmentDTO> getAllDepartments(){
        return departmentRepository.findAll().stream().map(DepartmentDTO::new).toList();
    }

    public DepartmentDTO getDepartmentById(Long id){
        Department foundDepartment = departmentRepository.findById(id).orElseThrow(()->new RuntimeException("No department found with id: "+id));

        return new DepartmentDTO(foundDepartment);
    }
    public String getDepartmentClassLeader(String name) {
        Department foundDepartment = departmentRepository.findByName(name);
        if (foundDepartment == null) return "Not found!";
        if (foundDepartment.getClassLeader() == null) return "No class leader assigned";
        return foundDepartment.getClassLeader().getEmail();
    }

    public DepartmentDTO createDepartment(Department department) {
        // Validate class leader BEFORE creating department
        User classLeader = null;
        if (department.getClassLeader() != null && department.getClassLeader().getId() != null) {
            classLeader = userRepository.findById(department.getClassLeader().getId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Verify user is a teacher (fixed logic: use AND, not OR)
            if (!classLeader.getRole().equals(Role.TEACHER) && !classLeader.getRole().equals(Role.SYSADMIN)) {
                throw new RuntimeException("Class leader must be a teacher or sysadmin");
            }
        }

        // Only create department after validation passes
        Department newDepartment = new Department();
        newDepartment.setName(department.getName());
        Department savedDepartment = departmentRepository.save(newDepartment);

        // Set the bidirectional relationship if classLeader exists
        if (classLeader != null) {
            classLeader.setClassLeaderOf(savedDepartment);
            userRepository.save(classLeader);
            savedDepartment.setClassLeader(classLeader);
        }

        return new DepartmentDTO(savedDepartment);
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
