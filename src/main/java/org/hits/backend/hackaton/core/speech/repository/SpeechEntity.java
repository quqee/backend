package org.hits.backend.hackaton.core.speech.repository;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SpeechEntity(
        UUID applicationId,
        OffsetDateTime scheduleTime,
        SpeechStatus status,
        String processId
) {
}
