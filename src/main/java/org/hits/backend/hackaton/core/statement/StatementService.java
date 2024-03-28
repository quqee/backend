package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.defect.DefectEntity;
import org.hits.backend.hackaton.core.defect.DefectRepository;
import org.hits.backend.hackaton.core.file.S3StorageService;
import org.hits.backend.hackaton.core.file.FileMetadata;
import org.hits.backend.hackaton.core.speech.SpeechService;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.core.user.service.UserService;
import org.hits.backend.hackaton.core.executors.service.ExecutorService;
import org.hits.backend.hackaton.public_interface.defect.DefectSmallDto;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.public_interface.file.UploadFileDto;
import org.hits.backend.hackaton.public_interface.statement.*;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.hits.backend.hackaton.public_interface.statement.StatementSmallDto;
import org.hits.backend.hackaton.public_interface.statement.StatementStatus;
import org.hits.backend.hackaton.public_interface.statement.UpdateStatementDto;
import org.hits.backend.hackaton.rest.statement.v1.response.StatementFullResponse;
import org.hits.backend.hackaton.rest.statement.v1.response.StatementSmallResponse;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StatementService {
    private final S3StorageService storageService;
    private final SpeechService speechService;
    private final StatementRepository statementRepository;
    private final DefectRepository defectRepository;
    private final UserService userService;
    private final ExecutorService executorService;

    public StatementSmallDto createStatement(CreateStatementDto dto) {
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
        var statement = statementRepository.getStatementById(statementId)
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));

        return mapToDto(statement);
    }

    public List<StatementSmallDto> getStatements(String statementStatus) {
        var statementsEntity = statementRepository.getStatements(statementStatus);
        return statementsEntity.stream().map(this::mapToDto).toList();
    }

    public void assignEmployeeToStatement(UserEntity authentication, AssignmentEmployee request) {
        var employee = userService.findUserById(request.userId());

        var statement = statementRepository.getStatementById(request.statementId())
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));

        if (!statement.status().equals(StatementStatus.OPEN)) {
            throw new ExceptionInApplication("Statement is not open", ExceptionType.INVALID);
        }

        if (!employee.organizationId().equals(authentication.organizationId())) {
            throw new ExceptionInApplication("You can't assign employee from another organization", ExceptionType.INVALID);
        }

        executorService.assignExecutor(request.statementId(), request.userId());
    }

    public StatementSmallDto updateStatement(UpdateStatementDto dto) {
        var oldEntity = statementRepository.getStatementById(dto.statementId())
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));

        if (!dto.organizationCreatorId().equals(oldEntity.organizationCreatorId())) {
            throw new ExceptionInApplication("You can't update statement from another organization", ExceptionType.INVALID);
        }

        var updatedStatement = new StatementEntity(
                oldEntity.statementId(),
                oldEntity.organizationCreatorId(),
                dto.organizationPerformerId(),
                oldEntity.creationDate(),
                dto.areaName(),
                dto.length(),
                dto.roadType(),
                dto.surfaceType(),
                dto.direction(),
                dto.deadline(),
                null,
                dto.status()
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

        return mapToDto(updatedStatement);
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

    public List<StatementSmallDto> getMyStatements(UserEntity userEntity) {
        var statements = Stream.concat(statementRepository.getStatementsByOrganizationId(userEntity.organizationId()),
                statementRepository.getStatementsByExecutorId(userEntity.id()));

        return  statements.map(this::mapToDto).toList();
    }

    public void deleteStatement(UUID statementId) {
        statementRepository.deleteStatement(statementId);
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

    private StatementSmallDto mapToDto(StatementEntity entity) {
        return new StatementSmallDto(
                entity.statementId(),
                entity.areaName(),
                entity.length(),
                entity.roadType(),
                entity.surfaceType(),
                entity.direction(),
                entity.deadline(),
                entity.creationDate(),
                entity.description(),
                entity.status(),
                entity.organizationPerformerId(),
                entity.organizationCreatorId()
        );
    }
}
