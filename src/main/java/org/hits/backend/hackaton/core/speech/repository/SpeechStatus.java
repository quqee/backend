package org.hits.backend.hackaton.core.speech.repository;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;

@RequiredArgsConstructor
public enum SpeechStatus {
    IN_PROCESS("IN_PROCESS"),
    EXECUTED("EXECUTED"),
    ;
    private final String status;

    public static SpeechStatus getStatusByName(String name) {
        try {
            return SpeechStatus.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new ExceptionInApplication("Invalid status name: " + name, ExceptionType.INVALID);
        }
    }
}
