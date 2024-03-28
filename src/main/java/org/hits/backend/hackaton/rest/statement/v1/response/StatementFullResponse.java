package org.hits.backend.hackaton.rest.statement.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record StatementFullResponse(
        @JsonProperty("statement_id")
        UUID statementId,
        @JsonProperty("area_name")
        String areaName,
        @JsonProperty("length")
        Double length,
        @JsonProperty("road_type")
        String roadType,
        @JsonProperty("surface_type")
        String surfaceType,
        @JsonProperty("direction")
        String direction,
        @JsonProperty("deadline")
        OffsetDateTime deadline,
        @JsonProperty("create_time")
        OffsetDateTime createdAt,
        @JsonProperty("description")
        String description,
        @JsonProperty("status")
        String status,
        @JsonProperty("organization_creator_id")
        UUID organizationCreatorId,
        @JsonProperty("organization_performer_id")
        UUID organizationPerformerId,
        @JsonProperty("defects")
        List<DefectSmallResponse> defects
) {
}
