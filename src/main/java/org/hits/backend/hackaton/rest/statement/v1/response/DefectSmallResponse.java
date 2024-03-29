package org.hits.backend.hackaton.rest.statement.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record DefectSmallResponse(
        @JsonProperty("id")
        UUID defectId,
        @JsonProperty("type")
        String defectType,
        @JsonProperty("status")
        String defectStatus,
        @JsonProperty("description")
        String description,
        Double latitude,
        Double longitude
) {
}
