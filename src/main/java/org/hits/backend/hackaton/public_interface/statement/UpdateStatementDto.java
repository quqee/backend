package org.hits.backend.hackaton.public_interface.statement;

import org.hits.backend.hackaton.core.statement.RoadType;
import org.hits.backend.hackaton.core.statement.SurfaceType;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateStatementDto(
        MultipartFile audio,
        String areaName,
        Double length,
        RoadType roadType,
        SurfaceType surfaceType,
        String direction,
        OffsetDateTime deadline,
        UUID organizationCreatorId,
        UUID statementId
) {
}
