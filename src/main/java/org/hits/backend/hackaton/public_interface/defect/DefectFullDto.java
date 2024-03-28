package org.hits.backend.hackaton.public_interface.defect;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record DefectFullDto(
        UUID defectId,
        Double latitude,
        Double longitude,
        DefectStatus status,
        DefectTypeDto type,
        Double defectDistance,
        OffsetDateTime creationDate,
        List<String> imagesBefore,
        List<String> imagesAfter
) {
}
