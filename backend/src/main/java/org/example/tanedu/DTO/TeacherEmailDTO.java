package org.example.tanedu.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tanedu.Model.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherEmailDTO {
    private String email;
    private Subject subject;
}
