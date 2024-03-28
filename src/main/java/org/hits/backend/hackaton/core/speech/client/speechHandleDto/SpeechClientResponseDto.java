package org.hits.backend.hackaton.core.speech.client.speechHandleDto;

import java.time.OffsetDateTime;

public record SpeechClientResponseDto(
        Boolean done,
        String id,
        OffsetDateTime createdAt,
        String createdBy,
        OffsetDateTime modifiedAt
) {
}
