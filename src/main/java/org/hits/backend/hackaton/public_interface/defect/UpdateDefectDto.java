package org.hits.backend.hackaton.public_interface.defect;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public record UpdateDefectDto(
        Double longitude,
        Double latitude,
        Integer defectTypeId,
        Double defectDistance,
        MultipartFile[] photos,
        UUID statementId,
        UUID userId,
        String address,
        UUID defectId,
        DefectStatus status
) {
}
