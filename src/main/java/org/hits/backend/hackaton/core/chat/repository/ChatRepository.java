package org.hits.backend.hackaton.core.chat.repository;

import org.hits.backend.hackaton.core.chat.repository.entity.ChatRoomEntity;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository {
    Optional<ChatRoomEntity> getChatRoom(UUID senderId, UUID receiverId);
    ChatRoomEntity createChatRoom(UUID senderId, UUID receiverId);
}
