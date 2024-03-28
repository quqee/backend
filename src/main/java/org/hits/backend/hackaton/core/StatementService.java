package org.hits.backend.hackaton.core;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.file.S3StorageService;
import org.hits.backend.hackaton.core.file.FileMetadata;
import org.hits.backend.hackaton.core.speech.SpeechService;
import org.hits.backend.hackaton.public_interface.file.UploadFileDto;
import org.hits.backend.hackaton.public_interface.statement.CreateStatementDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StatementService {
    private final S3StorageService storageService;
    private final SpeechService speechService;

    public UUID createStatement(CreateStatementDto dto) {
        var fileMetadata = new FileMetadata(
                "aga",
                dto.audio().getContentType(),
                dto.audio().getSize()
        );
        var uploadFileDto = new UploadFileDto(
                fileMetadata,
                dto.audio()
        );
        //TODO: id заявки
        storageService.uploadFile(uploadFileDto)
                .doOnSuccess(aVoid -> speechService.startProcessVoice(storageService.getDownloadLinkByName(fileMetadata.fileName()), UUID.randomUUID()))
                .subscribe();
        return UUID.randomUUID();
    }
}
