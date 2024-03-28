package org.hits.backend.hackaton.public_interface.statement;

import org.hits.backend.hackaton.core.statement.RoadType;
import org.hits.backend.hackaton.core.statement.SurfaceType;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StatementSmallDto(
        UUID statementId,
        String areaName,
        Double length,
        RoadType roadType,
        SurfaceType surfaceType,
        String direction,
        OffsetDateTime deadline,
        OffsetDateTime createTime,
        String description,
        StatementStatus statementStatus,
        UUID organizationPerformerId,
        UUID organizationCreatorId
) {
}
