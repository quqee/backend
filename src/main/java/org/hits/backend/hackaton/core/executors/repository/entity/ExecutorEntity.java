package org.hits.backend.hackaton.core.executors.repository.entity;

import java.util.UUID;

public record ExecutorEntity(
        UUID userId,
        UUID statementId
) {
}
