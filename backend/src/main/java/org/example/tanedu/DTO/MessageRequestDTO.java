package org.example.tanedu.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.tanedu.Model.Message;


@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDTO {
    private ReceiverInfo receiver;
    private String message;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ReceiverInfo {
        private String email;
    }

}
