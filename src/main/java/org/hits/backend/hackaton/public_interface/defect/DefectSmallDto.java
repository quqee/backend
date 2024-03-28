package org.hits.backend.hackaton.public_interface.defect;

import java.util.UUID;

public record DefectSmallDto(
        UUID defectId,
        DefectStatus status,
        DefectTypeDto type,
        String description
) {
}
