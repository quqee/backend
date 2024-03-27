package org.hits.backend.hackaton.websocket.chat.v1;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hits.backend.hackaton.core.message.repository.entity.MessageEntity;
import org.hits.backend.hackaton.core.message.service.MessageService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat")
    public void processMessage(@Payload RequestMessageDto message, @AuthenticationPrincipal UsernamePasswordAuthenticationToken authenticationToken) {
        MessageEntity savedMsg = messageService.save(message, ((UserEntity) authenticationToken.getPrincipal()).id());

        ResponseNotificationDto notification = new ResponseNotificationDto(
                savedMsg.messageId(),
                savedMsg.senderId(),
                savedMsg.recipientId(),
                savedMsg.content()
        );

        messagingTemplate.convertAndSend("/topic/" + message.recipientId() + "/messages", notification);
    }
}
