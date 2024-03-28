package org.hits.backend.hackaton.rest.statement.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record DefectSmallResponse(
        @JsonProperty("defect_id")
        UUID defectId,
        @JsonProperty("defect_type")
        String defectType,
        @JsonProperty("defect_status")
        String defectStatus,
        @JsonProperty("description")
        String description
) {
}
