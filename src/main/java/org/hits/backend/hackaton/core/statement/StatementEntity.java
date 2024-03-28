package org.hits.backend.hackaton.core.statement;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StatementEntity(
        UUID statementId,
        UUID organizationCreatorId,
        UUID organizationPerformerId,
        OffsetDateTime creationDate,
        String areaName,
        Double length,
        RoadType roadType,
        SurfaceType surfaceType,
        String direction,
        OffsetDateTime deadline,
        String description,
        StatementStatus status
) {
}
