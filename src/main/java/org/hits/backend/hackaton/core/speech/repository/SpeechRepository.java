package org.hits.backend.hackaton.core.speech.repository;

import java.util.List;
import java.util.UUID;

public interface SpeechRepository {
    List<SpeechEntity> getSpeechesNotProcessed();
    void updateSpeechStatus(UUID applicationId, SpeechStatus status);
    void createSpeech(SpeechEntity speechEntity);
}
