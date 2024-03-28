package org.hits.backend.hackaton.public_interface.statement;

import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

public record CreateStatementDto(
        OffsetDateTime deadlineTime,
        MultipartFile audio
) {
}
