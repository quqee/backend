package org.hits.backend.hackaton.rest.message.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

@Builder
public record MessageDto(
        @JsonProperty("message_id")
        UUID messageId,

        @JsonProperty("chat_room_id")
        UUID chatRoomId,

        @JsonProperty("sender_id")
        UUID senderId,

        @JsonProperty("recipient_id")
        UUID recipientId,

        String content,

        @JsonProperty("created_at")
        OffsetDateTime createdAt,

        @JsonProperty("my_message")
        boolean myMessage
) {
}
