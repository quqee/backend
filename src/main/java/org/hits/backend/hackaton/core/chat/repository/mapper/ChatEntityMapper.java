package org.hits.backend.hackaton.core.chat.repository.mapper;

import com.example.hackathon.public_.tables.records.ChatRoomRecord;
import org.hits.backend.hackaton.core.chat.repository.entity.ChatRoomEntity;
import org.jooq.RecordMapper;

public class ChatEntityMapper implements RecordMapper<ChatRoomRecord, ChatRoomEntity> {

    @Override
    public ChatRoomEntity map(ChatRoomRecord chatRoomRecord) {
        return new ChatRoomEntity(
                chatRoomRecord.getChatRoomId(),
                chatRoomRecord.getSenderId(),
                chatRoomRecord.getReceiverId()
        );
    }
}
