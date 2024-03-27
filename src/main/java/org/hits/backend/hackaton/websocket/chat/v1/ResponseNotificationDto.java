package org.hits.backend.hackaton.websocket.chat.v1;

import java.util.UUID;

public record ResponseNotificationDto(
        UUID notificationId,
        UUID senderId,
        UUID receiverId,
        String content
) {
}
