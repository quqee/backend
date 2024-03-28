package org.hits.backend.hackaton.core.defect;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.file.StorageService;
import org.hits.backend.hackaton.public_interface.defect.CreateDefectDto;
import org.hits.backend.hackaton.public_interface.defect.DefectFullDto;
import org.hits.backend.hackaton.public_interface.defect.DefectStatus;
import org.hits.backend.hackaton.public_interface.defect.UpdateDefectDto;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DefectService {
    private final DefectRepository defectRepository;
    private final StorageService storageService;

    public UUID createDefect(CreateDefectDto dto) {
        var defect = new DefectEntity(
                null,
                dto.statementId(),
                dto.longitude(),
                dto.latitude(),
                DefectStatus.OPEN,
                dto.defectTypeId(),
                dto.address(),
                OffsetDateTime.now(),
                dto.defectDistance()
        );

        var defectId = defectRepository.createDefect(defect);
        return null;
    }

    public void updateDefect(UpdateDefectDto dto) {
        var oldDefect = defectRepository.getDefectById(dto.defectId())
                .orElseThrow(() -> new ExceptionInApplication("Defect not found", ExceptionType.NOT_FOUND));

        var newDefect = new DefectEntity(
                oldDefect.applicationId(),
                oldDefect.statementId(),
                dto.longitude(),
                dto.latitude(),
                dto.status(),
                dto.defectTypeId(),
                dto.address(),
                oldDefect.createdAt(),
                dto.defectDistance()
        );

        defectRepository.updateDefect(newDefect);
    }

    public DefectFullDto getDefect(UUID defectId) {
        var defect = defectRepository.getDefectById(defectId);
        var images = defectRepository.getPhotosByDefectId(defectId);
        return null;
    }
}
