package org.hits.backend.hackaton.core.speech.client;

import org.hits.backend.hackaton.core.speech.client.speechHandleDto.SpeechClientResponseDto;
import org.hits.backend.hackaton.core.speech.client.speechStatusHandleDto.SpeechStatusDto;
import reactor.core.publisher.Mono;

public interface SpeechClient {
    Mono<SpeechClientResponseDto> sendToProcess(String fileUri);
}
