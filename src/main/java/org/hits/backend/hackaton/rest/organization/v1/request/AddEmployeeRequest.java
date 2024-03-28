package org.hits.backend.hackaton.rest.organization.v1.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AddEmployeeRequest(
        @NotBlank(message = "Invalid username: EMPTY username")
        @NotNull(message = "Invalid username: username is NULL")
        @Size(min = 3, max = 50, message = "Username should be between 3 and 50 characters")
        @JsonProperty("username")
        String username,

        @Email(message = "Email should be valid")
        @JsonProperty("email")
        String email,

        @NotBlank(message = "Invalid fullName: EMPTY fullName")
        @NotNull(message = "Invalid fullName: fullName is NULL")
        @JsonProperty("full_name")
        String fullName,

        @NotBlank(message = "Invalid password: EMPTY password")
        @NotNull(message = "Invalid password: password is NULL")
        @Size(min = 5, max = 50, message = "Password should be between 5 and 50 characters")
        @JsonProperty("password")
        String password
) {
}
