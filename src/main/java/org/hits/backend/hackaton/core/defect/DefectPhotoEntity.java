package org.hits.backend.hackaton.core.defect;

import java.util.UUID;

public record DefectPhotoEntity(
        String photoId,
        UUID defectId,
        boolean isReview
) {
}
