package org.hits.backend.hackaton.public_interface.statement;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AssignmentEmployee(
        @JsonProperty("statement_id")
        UUID statementId,

        @JsonProperty("user_id")
        UUID userId
) {
}
