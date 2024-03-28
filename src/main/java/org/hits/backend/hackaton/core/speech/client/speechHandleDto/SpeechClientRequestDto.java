package org.hits.backend.hackaton.core.speech.client.speechHandleDto;

public record SpeechClientRequestDto(
        SpeechClientConfigRequestDto config,
        SpeechClientAudioRequestDto audio
) {
}
