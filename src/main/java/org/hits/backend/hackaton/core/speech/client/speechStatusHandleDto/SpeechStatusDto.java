package org.hits.backend.hackaton.core.speech.client.speechStatusHandleDto;

/*
{
    "done": true,
    "response": {
        "@type": "type.googleapis.com/yandex.cloud.ai.stt.v2.LongRunningRecognitionResponse",
        "chunks": [
            {
                "alternatives": [
                    {
                        "words": [
                            {
                                "startTime": "0.919s",
                                "endTime": "1.280s",
                                "word": "например",
                                "confidence": 1
                            },
                            {
                                "startTime": "1.360s",
                                "endTime": "1.719s",
                                "word": "звуковой",
                                "confidence": 1
                            },
                            {
                                "startTime": "1.780s",
                                "endTime": "2.200s",
                                "word": "дорожки",
                                "confidence": 1
                            }
                        ],
                        "text": "например звуковой дорожки",
                        "confidence": 1
                    }
                ],
                "channelTag": "1"
            }
        ]
    },
    "id": "e03rjqs5ajem5dqsioqd",
    "createdAt": "2024-03-27T18:56:26Z",
    "createdBy": "aje78d98ir7cpartetqv",
    "modifiedAt": "2024-03-27T18:56:29Z"
}
*/

public record SpeechStatusDto(
        Boolean done,
        SpeechStatusResponseDto response,
        String id,
        String createdAt,
        String createdBy,
        String modifiedAt
) {
    public record SpeechStatusResponseDto(
            String type,
            ChunksDto[] chunks
    ) {
        public record ChunksDto(
                AlternativeDto[] alternatives,
                String channelTag
        ) {
            public record AlternativeDto(
                    WordDto[] words,
                    String text,
                    Integer confidence
            ) {
                public record WordDto(
                        String startTime,
                        String endTime,
                        String word,
                        Integer confidence
                ) {
                }
            }
        }
    }
}
