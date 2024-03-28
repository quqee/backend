package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;

@RequiredArgsConstructor
public enum RoadType {
    HIGHWAY("АВТОМАГИСТРАЛЬ"),
    EXPRESSWAY("СКОРОСТНАЯ_ДОРОГА"),
    ROAD("ОБЫЧНАЯ_ДОРОГА");

    public final String name;

    public static RoadType getTypeByName(String name) {
        try {
            return RoadType.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new ExceptionInApplication("Unknown road type: " + name, ExceptionType.INVALID);
        }
    }
}
