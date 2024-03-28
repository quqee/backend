package org.hits.backend.hackaton.rest.defect.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DefectTypeResponse(
        @JsonProperty("id") Integer id,
        @JsonProperty("name") String name,
        @JsonProperty("has_distance") Boolean hasDistance
) {
}
