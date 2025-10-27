package org.example.tanedu;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

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

    public boolean isOverlapping(String dur1, String dur2) {
        if (dur1 == null || dur2 == null) return false;
        if (dur1.equals(dur2)) return true;

        // Expecting format "HH:mm-HH:mm". If not parseable, fallback to equality check above.
        if (dur1.contains("-") && dur2.contains("-")) {
            try {
                String[] p1 = dur1.split("-");
                String[] p2 = dur2.split("-");
                LocalTime s1 = LocalTime.parse(p1[0].trim());
                LocalTime e1 = LocalTime.parse(p1[1].trim());
                LocalTime s2 = LocalTime.parse(p2[0].trim());
                LocalTime e2 = LocalTime.parse(p2[1].trim());

                // Overlap if intervals intersect (adjacent endpoints allowed)
                return s1.isBefore(e2) && s2.isBefore(e1);
            } catch (DateTimeParseException | ArrayIndexOutOfBoundsException ex) {
                return dur1.equals(dur2);
            }
        }

        return false;
    }
}
