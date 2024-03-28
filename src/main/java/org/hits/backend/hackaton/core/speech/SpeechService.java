package org.hits.backend.hackaton.core.speech;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.speech.client.SpeechClient;
import org.hits.backend.hackaton.core.speech.repository.SpeechEntity;
import org.hits.backend.hackaton.core.speech.repository.SpeechRepository;
import org.hits.backend.hackaton.core.speech.repository.SpeechStatus;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpeechService {
    private final SpeechClient speechClient;
    private final SpeechRepository speechRepository;

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
        speeches.forEach(speechEntity -> {
            speechClient.getStatus(speechEntity.processId())
                    .doOnSuccess(speechStatusDto -> {
                        if (speechStatusDto.done()) {
                            var text = speechStatusDto.response().alternatives()[0].text();
                            //TODO: обновить заявку

                            speechRepository.updateSpeechStatus(speechEntity.applicationId(), SpeechStatus.EXTRACTED);
                        }
                    })
                    .subscribe();
        });
    }
}
