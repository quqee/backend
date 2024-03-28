package org.hits.backend.hackaton.core.organization;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;

@RequiredArgsConstructor
public enum OrganizationType {
    CUSTOMER("CUSTOMER"),
    PERFORMER("PERFORMER"),
    ;

    public final String type;

    public static OrganizationType getTypeByName(String type) {
        try {
            return OrganizationType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new ExceptionInApplication("Invalid organization type: " + type, ExceptionType.INVALID);
        }
    }
}
