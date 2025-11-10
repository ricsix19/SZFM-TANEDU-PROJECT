package org.example.tanedu.Service;

import org.example.tanedu.DTO.MessageRequestDTO;
import org.example.tanedu.DTO.MessageResponseDTO;
import org.example.tanedu.Model.Department;
import org.example.tanedu.Model.Message;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.DepartmentRepository;
import org.example.tanedu.Repository.MessageRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private Utils utils;

    public MessageResponseDTO sendMessage(MessageRequestDTO request){
        User sender = userRepository.findByEmail(utils.getCurrentUserEmail());
        String receiverEmail = request.getReceiver().getEmail();
        User receiver = userRepository.findByEmail(receiverEmail);

        if (receiver == null) {
            throw new RuntimeException("No user found with email: " + receiverEmail);
        }

        String message = request.getMessage();

        Message newMessage = new Message();
        newMessage.setReceiver(receiver);
        newMessage.setSender(sender);
        newMessage.setValue(message);

        return new MessageResponseDTO(messageRepository.save(newMessage));
    }

    public MessageResponseDTO postToDepartment(Long deptId, Map<String, String> body){
        Department dept = departmentRepository.findById(deptId)
                .orElseThrow(() -> new IllegalArgumentException("department not found"));

        String senderIdStr = body.get("senderId");
        if (senderIdStr == null) {
            throw new IllegalArgumentException("senderId required");
        }

        Long senderId;
        try {
            senderId = Long.valueOf(senderIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid senderId");
        }

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("sender not found"));

        Message m = new Message();
        m.setValue(body.get("value"));
        m.setSender(sender);
        m.setDepartment(dept);
        Message saved = messageRepository.save(m);
        return new MessageResponseDTO(saved);
    }

    public List<MessageResponseDTO> getDepartmentMessages(Long deptId){
        if (departmentRepository.findById(deptId).isEmpty()) {
            throw new IllegalArgumentException("department not found");
        }
        List<Message> messages = messageRepository.findByDepartmentIdOrderByCreatedAtAsc(deptId);
        return messages.stream()
                .map(MessageResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<MessageResponseDTO> getMessagesByCurrentUser(){
        User currentUser = userRepository.findByEmail(utils.getCurrentUserEmail());

        List<Message> received = messageRepository.findAllByReceiver(currentUser);
        List<Message> sent = messageRepository.findAllBySender(currentUser);

        return Stream.concat(received.stream(), sent.stream())
                .distinct()
                .map(MessageResponseDTO::new)
                .collect(Collectors.toList());
    }

}
