package org.example.tanedu.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tanedu.Model.Message;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponseDTO {
    private Long id;
    private String message;
    private Long senderId;
    private String senderFullName;
    private String senderEmail;
    private Long receiverId;
    private String receiverFullName;
    private String receiverEmail;
    private LocalDateTime createdAt;

    public MessageResponseDTO(Message message) {
        this.id = message.getId();
        this.message = message.getValue();
        this.senderId = message.getSender().getId();
        this.senderFullName = message.getSender().getFullName();
        this.senderEmail = message.getSender().getEmail();
        this.receiverId = message.getReceiver() != null ? message.getReceiver().getId() : null;
        this.receiverFullName = message.getReceiver() != null ? message.getReceiver().getFullName() : null;
        this.receiverEmail = message.getReceiver() != null ? message.getReceiver().getEmail() : null;
        this.createdAt = message.getCreatedAt();
    }
}
