package org.hits.backend.hackaton.rest.statement.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StatementSmallResponse(
        @JsonProperty("statement_id") UUID statementId,
        @JsonProperty("area_name") String areaName,
        @JsonProperty("length") Double length,
        @JsonProperty("road_type") String roadType,
        @JsonProperty("surface_type") String surfaceType,
        @JsonProperty("direction") String direction,
        @JsonProperty("deadline") OffsetDateTime deadline,
        @JsonProperty("create_time") OffsetDateTime createTime,
        @JsonProperty("description") String description,
        @JsonProperty("status") String statementStatus,
        @JsonProperty("organization_performer_id") UUID organizationPerformerId,
        @JsonProperty("organization_creator_id") UUID organizationCreatorId
) {
}
