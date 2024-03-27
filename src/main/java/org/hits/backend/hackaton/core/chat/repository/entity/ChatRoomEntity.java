package org.hits.backend.hackaton.core.chat.repository.entity;

import java.util.UUID;

public record ChatRoomEntity(
        UUID chatRoomId,
        UUID senderId,
        UUID receiverId
) {
}
