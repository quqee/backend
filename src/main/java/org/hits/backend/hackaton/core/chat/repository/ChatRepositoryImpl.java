package org.hits.backend.hackaton.core.chat.repository;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.chat.repository.entity.ChatRoomEntity;
import org.hits.backend.hackaton.core.chat.repository.mapper.ChatEntityMapper;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

import static com.example.hackathon.public_.Tables.CHAT_ROOM;

@Repository
@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepository {
    private static final ChatEntityMapper CHAT_ENTITY_MAPPER = new ChatEntityMapper();
    private final DSLContext create;

    @Override
    public Optional<ChatRoomEntity> getChatRoom(UUID senderId, UUID receiverId) {
        return create.selectFrom(CHAT_ROOM)
                .where(CHAT_ROOM.SENDER_ID.eq(senderId).and(CHAT_ROOM.RECEIVER_ID.eq(receiverId)))
                .fetchOptional(CHAT_ENTITY_MAPPER);
    }

    @Override
    public ChatRoomEntity createChatRoom(UUID senderId, UUID receiverId) {
        return create.insertInto(CHAT_ROOM)
                .set(CHAT_ROOM.SENDER_ID, senderId)
                .set(CHAT_ROOM.RECEIVER_ID, receiverId)
                .returning(CHAT_ROOM.CHAT_ROOM_ID, CHAT_ROOM.SENDER_ID, CHAT_ROOM.RECEIVER_ID)
                .fetchOne(CHAT_ENTITY_MAPPER);
    }
}
