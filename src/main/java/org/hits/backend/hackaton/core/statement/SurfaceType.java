package org.hits.backend.hackaton.core.statement;

import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;

@RequiredArgsConstructor
public enum SurfaceType {
    ASPHALT("АСФАЛЬТ"),
    COBBLESTONE("БРУСЧАТКА"),
    CRUSHED_STONE("ЩЕБЕНЬ"),
    GROUND("ГРУНТ"),
    SAND("ПЕСОК"),
    CONCRETE("БЕТОН"),
    REINFORCED_CONCRETE("ЖЕЛЕЗОБЕТОН"),
    COMBINED("КОМБИНИРОВАННОЕ"),
    OTHER("ДРУГОЕ");

    public final String type;

    public static SurfaceType getTypeByName(String name) {
        try {
            return SurfaceType.valueOf(name);
        } catch (IllegalArgumentException e) {
            throw new ExceptionInApplication("Invalid surface type name: " + name, ExceptionType.INVALID);
        }
    }
}
