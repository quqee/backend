package org.hits.backend.hackaton.core.speech.client;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.speech.client.speechStatusHandleDto.SpeechStatusDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class OperationClientImpl implements OperationClient {
    private final WebClient webClient;
    @Value("${yandexApi.statusEndpoint}")
    private String statusEndpoint;

    @Override
    public Mono<SpeechStatusDto> getStatus(String id) {
        return webClient
                .get()
                .uri(statusEndpoint, id)
                .retrieve()
                .bodyToMono(SpeechStatusDto.class);
    }
}
