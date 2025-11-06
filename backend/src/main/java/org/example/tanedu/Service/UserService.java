// language: java
package org.example.tanedu.Service;

import org.example.tanedu.DTO.UserDTO;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.Subject;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.DepartmentRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private Utils utils;

    public List<User> getAllUsers(){
        if (utils.isCurrentUser("SYSADMIN")) return userRepository.findAll();
        return Collections.emptyList();
    }
    public Role getCurrentUserRole(){
        User foundUser = userRepository.findByEmail(utils.getCurrentUserEmail());
        return foundUser.getRole();
    }

    public List<String> getAllTeacherEmails(){
        List<User> foundTeachers = userRepository.findAllByRole(Role.TEACHER);
        return foundTeachers.stream().map(User::getEmail).collect(Collectors.toList());
    }
    public List<String> getAllStudentsEmail(){
        List<User> foundTeachers = userRepository.findAllByRole(Role.STUDENT);
        return foundTeachers.stream().map(User::getEmail).collect(Collectors.toList());
    }
    public List<Subject> getAllSubjects(){
        return userRepository.findAllByRole(Role.TEACHER).stream()
                .map(User::getSubject)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<String> getAllAvailableSubjects(){
        return Arrays.stream(Subject.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    public UserDTO getCurrentUser(){
        return new UserDTO(userRepository.findByEmail(utils.getCurrentUserEmail()));
    }

    public User getUserById(Long id){
        if (utils.isCurrentUser("SYSADMIN")) return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: "+id));

        return userRepository.findByEmail(utils.getCurrentUserEmail());
    }

    public List<UserDTO> getAllStudentsByDepartment(String department){
        Department foundDepartment = departmentRepository.findByName(department);
        return userRepository.findAllByDepartment(foundDepartment).stream().map(UserDTO::new).toList();
    }

    public User updateUserById(Long id, User user) {
        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        boolean isAdmin = utils.isCurrentUser("SYSADMIN");
        boolean isOwnProfile = foundUser.getEmail().equals(utils.getCurrentUserEmail());

        if (!isAdmin && !isOwnProfile) throw new RuntimeException("You don't have permission to edit other users");

        if (user.getSubject() != null) {
            Role targetRole = user.getRole() != null ? user.getRole() : foundUser.getRole();
            if (targetRole != Role.TEACHER) {
                throw new RuntimeException("Only users with role TEACHER can have a subject");
            }
            foundUser.setSubject(user.getSubject());
        }

        if (user.getRole() != null) {
            foundUser.setRole(user.getRole());
            if (user.getRole() != Role.TEACHER) {
                foundUser.setSubject(null);
            }
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {foundUser.setPassword(encoder.encode(user.getPassword()));}
        if (user.getDepartment() != null) {foundUser.setDepartment(user.getDepartment());}
        if (user.getEmail() != null) {foundUser.setEmail(user.getEmail());}
        if (user.getLastName() != null) {foundUser.setLastName(user.getLastName());}
        if (user.getFirstName() != null) {foundUser.setFirstName(user.getFirstName());}
        if (user.getBirthDate() != null) {foundUser.setBirthDate(user.getBirthDate());}

        return userRepository.save(foundUser);
    }

    public String deleteUserById(Long id){
        User foundUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: "+id));
        if (utils.isCurrentUser("SYSADMIN")){
            userRepository.delete(foundUser);
            return "Successfully deleted";
        }
        return "Deletion failed";

    }
}
