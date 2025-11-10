package org.example.tanedu.Controller;

import org.example.tanedu.DTO.MessageRequestDTO;
import org.example.tanedu.DTO.MessageResponseDTO;
import org.example.tanedu.Model.Message;
import org.example.tanedu.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/sendMessage")
    public MessageResponseDTO sendMessage(@RequestBody MessageRequestDTO request){
        return messageService.sendMessage(request);
    }

    @GetMapping("/getMessagesByCurrentUser")
    public List<MessageResponseDTO> sendMessage(){
        return messageService.getMessagesByCurrentUser();
    }

    @GetMapping("/departments/{deptId}/messages")
    public ResponseEntity<List<MessageResponseDTO>> getDepartmentMessages(@PathVariable Long deptId) {
        try {
            return ResponseEntity.ok(messageService.getDepartmentMessages(deptId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/departments/{deptId}/messages")
    public ResponseEntity<?> postToDepartment(@PathVariable Long deptId, @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(messageService.postToDepartment(deptId, body));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
