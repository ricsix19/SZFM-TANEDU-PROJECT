// java
package org.example.tanedu;

import org.example.tanedu.Model.Role;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class TanEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(TanEduApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            User foundAdmin = userRepository.findByEmail("tesztadmin@gmail.com");
            if (foundAdmin == null) {
                User admin = new User();
                admin.setEmail("tesztadmin@gmail.com");
                admin.setFirstName("First");
                admin.setLastName("Admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setBirthDate(new Date(2002 - 1900, 11, 24));
                admin.setRole(Role.SYSADMIN);
                userRepository.save(admin);
            } else {
                userRepository.findAll().forEach(user -> {
                    String pw = user.getPassword();
                    if (pw == null) return;
                    if (!(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
                        user.setPassword(passwordEncoder.encode(pw));
                        userRepository.save(user);
                    }
                });
            }
        };
    }
}
