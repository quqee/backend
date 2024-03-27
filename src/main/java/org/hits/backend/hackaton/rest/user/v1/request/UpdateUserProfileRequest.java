package org.hits.backend.hackaton.rest.user.v1.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserProfileRequest(
        @Email(message = "Email should be valid")
        String email,

        @JsonProperty("full_name")
        @NotBlank(message = "Invalid fullName: EMPTY fullName")
        @NotNull(message = "Invalid fullName: fullName is NULL")
        String fullName
) {
}
