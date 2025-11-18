package org.example.tanedu.Controller;

import org.example.tanedu.DTO.MugRequestDTO;
import org.example.tanedu.Service.MugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/mug")
public class MugController {

    @Autowired
    private MugService mugService;

    @GetMapping("/status")
    public Map<String, Boolean> getStatus(@RequestParam Long courseId) {
        return mugService.getStatus(courseId);
    }

    @PostMapping("/send")
    public ResponseEntity<?> sendMug(@RequestBody MugRequestDTO request) {
        if (request == null || request.getCourseId() == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "courseId required"));
        }
        return mugService.sendMug(request.getCourseId());
    }
}
