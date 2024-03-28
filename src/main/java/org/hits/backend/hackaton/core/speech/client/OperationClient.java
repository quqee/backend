package org.hits.backend.hackaton.core.speech.client;

import org.hits.backend.hackaton.core.speech.client.speechStatusHandleDto.SpeechStatusDto;
import reactor.core.publisher.Mono;

public interface OperationClient {
    Mono<SpeechStatusDto> getStatus(String id);
}
