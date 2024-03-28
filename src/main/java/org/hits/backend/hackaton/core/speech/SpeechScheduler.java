package org.hits.backend.hackaton.core.speech;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpeechScheduler {
    private final SpeechService speechService;

    @Scheduled(fixedRateString = "${scheduler.updateTextFixedRate}")
    public void updateSpeechText() {
        speechService.handleSpeechText();
    }
}
