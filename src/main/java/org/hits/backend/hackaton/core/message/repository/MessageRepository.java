package org.hits.backend.hackaton.core.message.repository;

import org.hits.backend.hackaton.core.message.repository.entity.MessageEntity;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface MessageRepository {
    MessageEntity save(MessageEntity messageEntity);
    List<MessageEntity> findChatMessages(UUID chatRoomId, PageRequest pageRequest);
    int countChatMessages(UUID chatRoomId);
}
