package org.example.tanedu.Controller;

import org.example.tanedu.DTO.UserDTO;
import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.example.tanedu.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAll")
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/getCurrentUser")
    public UserDTO getCurrentUser(){
        return userService.getCurrentUser();
    }
    @GetMapping("/getCurrentUserRole")
    public Role getCurrentUserRole(){
        return userService.getCurrentUserRole();
    }

    @GetMapping("/getById/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/update/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody User user){
        return userService.updateUserById(id, user);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable Long id){
        return userService.deleteUserById(id);
    }
}
