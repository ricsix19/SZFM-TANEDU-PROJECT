package org.example.tanedu.Repository;

import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);

    User findByFirstNameContainingIgnoreCase(String firstName);

    List<User> findAllByRole(Role role);
}
