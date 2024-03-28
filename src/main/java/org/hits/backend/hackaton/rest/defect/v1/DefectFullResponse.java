package org.hits.backend.hackaton.rest.defect.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record DefectFullResponse(
        @JsonProperty("id") UUID defectId,
        @JsonProperty("latitude") Double latitude,
        @JsonProperty("longitude") Double longitude,
        @JsonProperty("status") String status,
        @JsonProperty("type") String type,
        @JsonProperty("distance") Double defectDistance,
        @JsonProperty("create_date") OffsetDateTime creationDate,
        @JsonProperty("pictures") List<String> images
) {
}
