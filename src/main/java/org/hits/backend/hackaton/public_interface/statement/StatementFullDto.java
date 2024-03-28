package org.hits.backend.hackaton.public_interface.statement;

import org.hits.backend.hackaton.core.statement.RoadType;
import org.hits.backend.hackaton.core.statement.SurfaceType;
import org.hits.backend.hackaton.public_interface.defect.DefectSmallDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record StatementFullDto(
        UUID statementId,
        String areaName,
        Double length,
        RoadType roadType,
        SurfaceType surfaceType,
        String direction,
        OffsetDateTime deadline,
        OffsetDateTime createdAt,
        String description,
        StatementStatus status,
        UUID organizationCreatorId,
        UUID organizationPerformerId,
        List<DefectSmallDto> defects
) {
}
