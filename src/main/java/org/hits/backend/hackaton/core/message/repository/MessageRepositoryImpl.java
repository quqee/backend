package org.hits.backend.hackaton.core.message.repository;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.message.repository.entity.MessageEntity;
import org.hits.backend.hackaton.core.message.repository.mapper.MessageEntityMapper;
import org.jooq.DSLContext;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.example.hackathon.public_.Tables.MESSAGE;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {
    private static final MessageEntityMapper MESSAGE_ENTITY_MAPPER = new MessageEntityMapper();
    private final DSLContext create;

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        return create.insertInto(MESSAGE)
                .set(MESSAGE.CHAT_ROOM_ID, messageEntity.chatRoomId())
                .set(MESSAGE.SENDER_ID, messageEntity.senderId())
                .set(MESSAGE.RECEIVER_ID, messageEntity.recipientId())
                .set(MESSAGE.CONTENT, messageEntity.content())
                .set(MESSAGE.CREATED_AT, messageEntity.createdAt().toLocalDateTime())
                .returning(MESSAGE.MESSAGE_ID, MESSAGE.SENDER_ID, MESSAGE.RECEIVER_ID, MESSAGE.CONTENT, MESSAGE.CREATED_AT)
                .fetchOne(MESSAGE_ENTITY_MAPPER);
    }

    @Override
    public List<MessageEntity> findChatMessages(UUID chatRoomId, PageRequest pageRequest) {
        var offset = pageRequest.getPageNumber() * pageRequest.getPageSize();
        var limit = pageRequest.getPageSize();

        return create.selectFrom(MESSAGE)
                .where(MESSAGE.CHAT_ROOM_ID.eq(chatRoomId))
                .orderBy(MESSAGE.CREATED_AT.desc())
                .limit(limit)
                .offset(offset)
                .fetch(MESSAGE_ENTITY_MAPPER);
    }

    @Override
    public int countChatMessages(UUID chatRoomId) {
        return create.fetchCount(MESSAGE, MESSAGE.CHAT_ROOM_ID.eq(chatRoomId));
    }

}
