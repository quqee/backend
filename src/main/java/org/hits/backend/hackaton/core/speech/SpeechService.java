package org.hits.backend.hackaton.core.speech;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.speech.client.OperationClient;
import org.hits.backend.hackaton.core.statement.StatementEntity;
import org.hits.backend.hackaton.core.statement.StatementRepository;
import org.hits.backend.hackaton.core.speech.client.SpeechClient;
import org.hits.backend.hackaton.core.speech.repository.SpeechEntity;
import org.hits.backend.hackaton.core.speech.repository.SpeechRepository;
import org.hits.backend.hackaton.core.speech.repository.SpeechStatus;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpeechService {
    private final SpeechClient speechClient;
    private final OperationClient operationClient;
    private final SpeechRepository speechRepository;
    private final StatementRepository statementRepository;

    public void startProcessVoice(String fileUri, UUID applicationId) {
        speechClient.sendToProcess(fileUri)
                .doOnSuccess(speechClientResponseDtoSignal -> {
                    var speechEntity = new SpeechEntity(
                            applicationId,
                            OffsetDateTime.now().plusSeconds(30),
                            SpeechStatus.IN_PROCESS,
                            speechClientResponseDtoSignal.id()
                    );
                    speechRepository.createSpeech(speechEntity);
                })
                .subscribe();
    }

    public void handleSpeechText() {
        var speeches = speechRepository.getSpeechesNotProcessed();
        speeches.forEach(speechEntity -> operationClient.getStatus(speechEntity.processId())
                .doOnSuccess(speechStatusDto -> {
                    if (speechStatusDto.done()) {
                        var text = speechStatusDto.response().chunks()[0].alternatives()[0].text();
                        updateStatementText(speechEntity.applicationId(), text);
                        speechRepository.updateSpeechStatus(speechEntity.applicationId(), SpeechStatus.EXECUTED);
                    }
                })
                .subscribe());
    }

    public void updateStatementText(UUID statementId, String text) {
        var oldStatement = statementRepository.getStatementById(statementId)
                .orElseThrow(() -> new ExceptionInApplication("Statement not found", ExceptionType.NOT_FOUND));

        var newStatement = new StatementEntity(
                oldStatement.statementId(),
                oldStatement.organizationCreatorId(),
                oldStatement.organizationPerformerId(),
                oldStatement.creationDate(),
                oldStatement.areaName(),
                oldStatement.length(),
                oldStatement.roadType(),
                oldStatement.surfaceType(),
                oldStatement.direction(),
                oldStatement.deadline(),
                text,
                oldStatement.status()
        );
        statementRepository.updateStatement(newStatement);
    }
}
