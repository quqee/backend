package org.hits.backend.hackaton.core.chat.service;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.chat.repository.ChatRepository;
import org.hits.backend.hackaton.core.chat.repository.entity.ChatRoomEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRepository chatRepository;

    public UUID getChatRoomId(UUID senderId, UUID receiverId) {
        return chatRepository.getChatRoom(senderId, receiverId)
                .map(ChatRoomEntity::chatRoomId)
                .orElseGet(() -> chatRepository.createChatRoom(senderId, receiverId).chatRoomId());
    }
}
