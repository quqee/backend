package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.file.S3StorageService;
import org.hits.backend.hackaton.core.file.FileMetadata;
import org.hits.backend.hackaton.core.speech.SpeechService;
import org.hits.backend.hackaton.public_interface.file.UploadFileDto;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementService {
    private final S3StorageService storageService;
    private final SpeechService speechService;
    private final StatementRepository statementRepository;

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
}
