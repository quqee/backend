package org.hits.backend.hackaton.core.user.repository.entity;

import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;

public enum UserAuthoritiesEnum {
    DEFAULT,
    ;

    public static UserAuthoritiesEnum getAuthoritiesByName(String name) {
        try {
            return UserAuthoritiesEnum.valueOf(name);
        } catch (Exception e) {
            throw new ExceptionInApplication("Данного типа привелегии не существует", ExceptionType.INVALID);
        }
    }
}
