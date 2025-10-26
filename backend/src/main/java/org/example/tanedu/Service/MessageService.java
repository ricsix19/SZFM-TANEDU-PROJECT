package org.example.tanedu.Service;

import org.example.tanedu.DTO.MessageRequestDTO;
import org.example.tanedu.DTO.MessageResponseDTO;
import org.example.tanedu.Model.Message;
import org.example.tanedu.Model.User;
import org.example.tanedu.Repository.MessageRepository;
import org.example.tanedu.Repository.UserRepository;
import org.example.tanedu.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
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
