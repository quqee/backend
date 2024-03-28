package org.hits.backend.hackaton.public_interface.defect;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;

@RequiredArgsConstructor
public enum DefectStatus {
    IN_PROCESS("IN_PROCESS"),
    REJECTED("REJECTED"),
    COMPLETED("COMPLETED"),
    ;

    public final String status;

    public static DefectStatus getStatusByName(String status) {
        try {
            return DefectStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new ExceptionInApplication("Invalid status: " + status, ExceptionType.INVALID);
        }
    }
}
