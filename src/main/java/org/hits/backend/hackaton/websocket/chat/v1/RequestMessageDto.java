package org.hits.backend.hackaton.websocket.chat.v1;

import lombok.Builder;

import java.util.UUID;

@Builder
public record RequestMessageDto(
        UUID recipientId,
        String content
) {}
