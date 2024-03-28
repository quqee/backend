package org.hits.backend.hackaton.public_interface.defect;

public record DefectTypeDto(
        Integer id,
        String name,
        Boolean hasDistance
) {
}
