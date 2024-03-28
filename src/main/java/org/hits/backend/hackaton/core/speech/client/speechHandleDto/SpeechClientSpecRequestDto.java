package org.hits.backend.hackaton.core.speech.client.speechHandleDto;

public record SpeechClientSpecRequestDto(
        String languageCode,
        String model,
        Boolean profanityFilter,
        Boolean literature_text,
        String audioEncoding,
        Integer sampleRateHertz,
        Integer audioChannelCount,
        Boolean rawResults
) {
}
