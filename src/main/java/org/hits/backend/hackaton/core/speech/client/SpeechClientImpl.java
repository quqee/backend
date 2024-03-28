package org.hits.backend.hackaton.core.speech.client;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.core.speech.client.speechHandleDto.SpeechClientAudioRequestDto;
import org.hits.backend.hackaton.core.speech.client.speechHandleDto.SpeechClientConfigRequestDto;
import org.hits.backend.hackaton.core.speech.client.speechHandleDto.SpeechClientRequestDto;
import org.hits.backend.hackaton.core.speech.client.speechHandleDto.SpeechClientResponseDto;
import org.hits.backend.hackaton.core.speech.client.speechHandleDto.SpeechClientSpecRequestDto;
import org.hits.backend.hackaton.core.speech.client.speechStatusHandleDto.SpeechStatusDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class SpeechClientImpl implements SpeechClient {
    private final WebClient webClient;
    @Value("${yandexApi.speechEndpoint}")
    private String speechEndpoint;

    @Override
    public Mono<SpeechClientResponseDto> sendToProcess(String fileUri) {
        var body = new SpeechClientRequestDto(
                new SpeechClientConfigRequestDto(new SpeechClientSpecRequestDto(
                        "ru-RU",
                        "general",
                        true,
                        false,
                        "MP3",
                        48000,
                        null,
                        false
                )),
                new SpeechClientAudioRequestDto(fileUri)
        );
        return webClient
                .post()
                .uri(speechEndpoint)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(SpeechClientResponseDto.class);
    }

}
