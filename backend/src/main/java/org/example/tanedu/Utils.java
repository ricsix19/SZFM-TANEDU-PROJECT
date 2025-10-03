package org.example.tanedu;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public boolean isCurrentUser(String role) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals(role));
    }

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public String getCurrentUserEmail() {
        UserDetails userDetails = (UserDetails) getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
