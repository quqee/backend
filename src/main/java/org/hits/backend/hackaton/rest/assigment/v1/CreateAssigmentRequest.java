package org.hits.backend.hackaton.rest.assigment.v1;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record CreateAssigmentRequest(
        @JsonProperty("statement_id")
        UUID statementId,
        @JsonProperty("emails")
        List<String> emails
) {
}
