package org.hits.backend.hackaton.public_interface.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDto(
        UUID id,
        String email,

        @JsonProperty("organization_id")
        UUID organizationId,

        @JsonProperty("organization_name")
        String organizationName,

        @JsonProperty("full_name")
        String fullName,
        @JsonProperty("online_status")
        boolean onlineStatus
) {
}
