package org.hits.backend.hackaton.rest.organization.v1.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeDto(

        @JsonProperty("user_id")
        UUID user_id,

        String username,

        @JsonProperty("full_name")
        String fullName,

        String email
) {
}
