package org.hits.backend.hackaton.configiration.client;

import org.hits.backend.hackaton.core.speech.client.SpeechClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean
    public SpeechClientImpl webSpeechClient(
            @Value("${yandexApi.uri}") String baseUrl,
            @Value("${yandexApi.token}") String token
    ) {
        return new SpeechClientImpl(WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Api-Key " + token)
                .build());
    }
}
