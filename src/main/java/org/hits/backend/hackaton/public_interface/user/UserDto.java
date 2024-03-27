package org.hits.backend.hackaton.public_interface.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record UserDto(
        UUID id,
        String username,
        String email,
        @JsonProperty("full_name")
        String fullName,
        @JsonProperty("online_status")
        boolean onlineStatus
) {
}
