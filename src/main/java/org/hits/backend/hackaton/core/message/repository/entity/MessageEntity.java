package org.hits.backend.hackaton.core.message.repository.entity;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record MessageEntity(
        UUID messageId,
        UUID chatRoomId,
        UUID senderId,
        UUID recipientId,
        String content,
        OffsetDateTime createdAt
) {
}
