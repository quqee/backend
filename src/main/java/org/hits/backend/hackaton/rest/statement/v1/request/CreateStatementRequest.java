package org.hits.backend.hackaton.rest.statement.v1.request;

import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;

public record CreateStatementRequest(
        OffsetDateTime deadlineTime,
        MultipartFile audio
) {
}
