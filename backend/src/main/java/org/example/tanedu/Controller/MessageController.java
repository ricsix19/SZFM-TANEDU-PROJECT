package org.example.tanedu.Controller;

import org.example.tanedu.DTO.MessageRequestDTO;
import org.example.tanedu.DTO.MessageResponseDTO;
import org.example.tanedu.Model.Message;
import org.example.tanedu.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
