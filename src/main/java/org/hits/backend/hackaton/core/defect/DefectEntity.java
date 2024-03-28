package org.hits.backend.hackaton.core.defect;

import org.hits.backend.hackaton.public_interface.defect.DefectStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DefectEntity(
        UUID applicationId,
        UUID statementId,
        Double longitude,
        Double latitude,
        DefectStatus status,
        Integer defectStatusId,
        String address,
        OffsetDateTime createdAt,
        Double defectDistance
) {
}
