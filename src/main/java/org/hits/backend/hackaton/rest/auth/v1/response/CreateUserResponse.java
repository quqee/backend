package org.hits.backend.hackaton.rest.auth.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CreateUserResponse(
        UUID id,
        String username,
        String email,
        @JsonProperty("full_name")
        String fullName
) {
}
