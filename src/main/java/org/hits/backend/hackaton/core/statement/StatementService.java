package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.defect.DefectEntity;
import org.hits.backend.hackaton.core.defect.DefectRepository;
import org.hits.backend.hackaton.core.file.S3StorageService;
import org.hits.backend.hackaton.core.file.FileMetadata;
import org.hits.backend.hackaton.core.speech.SpeechService;
import org.hits.backend.hackaton.public_interface.defect.DefectSmallDto;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.file.UploadFileDto;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.hits.backend.hackaton.public_interface.statement.StatementStatus;
import org.hits.backend.hackaton.public_interface.statement.UpdateStatementDto;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementService {
    private final S3StorageService storageService;
    private final SpeechService speechService;
    private final StatementRepository statementRepository;
    private final DefectRepository defectRepository;

    public UUID createStatement(CreateStatementDto dto) {
        var newStatement = new StatementEntity(
                null,
                dto.organizationCreatorId(),
                null,
                OffsetDateTime.now(),
                dto.areaName(),
                dto.length(),
                dto.roadType(),
                dto.surfaceType(),
                dto.direction(),
                dto.deadline(),
                null,
                StatementStatus.OPEN
        );
        var statementId = statementRepository.createStatement(newStatement);

        var fileMetadata = new FileMetadata(
                String.format("statement_%s_audio", statementId),
                dto.audio().getContentType(),
                dto.audio().getSize()
        );
        var uploadFileDto = new UploadFileDto(
                fileMetadata,
                dto.audio()
        );
        storageService.uploadFile(uploadFileDto)
                .doOnSuccess(aVoid ->
                        speechService.startProcessVoice(storageService.getDownloadLinkByName(fileMetadata.fileName()), statementId))
                .subscribe();
        return statementId;
    }

    public void updateStatement(UpdateStatementDto dto) {
        var oldEntity = statementRepository.getStatementById(dto.statementId())
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));

        if (!dto.organizationCreatorId().equals(oldEntity.organizationCreatorId())) {
            throw new ExceptionInApplication("You can't update statement from another organization", ExceptionType.INVALID);
        }

        var updatedStatement = new StatementEntity(
                oldEntity.statementId(),
                oldEntity.organizationCreatorId(),
                oldEntity.organizationPerformerId(),
                oldEntity.creationDate(),
                dto.areaName(),
                dto.length(),
                dto.roadType(),
                dto.surfaceType(),
                dto.direction(),
                dto.deadline(),
                oldEntity.description(),
                oldEntity.status()
        );

        var fileMetadata = new FileMetadata(
                String.format("statement_%s_audio", oldEntity.statementId()),
                dto.audio().getContentType(),
                dto.audio().getSize()
        );
        var uploadFileDto = new UploadFileDto(
                fileMetadata,
                dto.audio()
        );
        storageService.uploadFile(uploadFileDto)
                .doOnSuccess(aVoid ->
                        speechService.startProcessVoice(storageService.getDownloadLinkByName(fileMetadata.fileName()), oldEntity.statementId()))
                .subscribe();

        statementRepository.updateStatement(updatedStatement);
    }

    public StatementFullDto getFullDto(UUID statementId) {
        var statement = statementRepository.getStatementById(statementId)
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));
        var defects = defectRepository.getDefectsByStatementId(statementId);

        return new StatementFullDto(
                statement.statementId(),
                statement.areaName(),
                statement.length(),
                statement.roadType(),
                statement.surfaceType(),
                statement.direction(),
                statement.deadline(),
                statement.creationDate(),
                statement.description(),
                statement.status(),
                statement.organizationCreatorId(),
                statement.organizationPerformerId(),
                defects.stream().map(this::mapToDto).toList()
        );
    }

    private DefectSmallDto mapToDto(DefectEntity entity) {
        var type = defectRepository.getDefectTypeById(entity.defectStatusId())
                .orElseThrow(() -> new ExceptionInApplication("Defect type not found", ExceptionType.NOT_FOUND));
        return new DefectSmallDto(
                entity.applicationId(),
                entity.status(),
                type,
                entity.address()
        );
    }

    public void deleteStatement(UUID statementId) {
        statementRepository.deleteStatement(statementId);
    }
}
