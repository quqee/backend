package org.hits.backend.hackaton.public_interface.organization;

import lombok.Builder;

@Builder
public record EmployeeRequest(
        String username,
        String email,
        String fullName,
        String password
) {
}
