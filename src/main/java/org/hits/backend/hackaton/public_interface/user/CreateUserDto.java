package org.hits.backend.hackaton.public_interface.user;

import java.util.UUID;

public record CreateUserDto(
        String username,
        String password,
        String email,
        String fullName,
        UUID organizationId
) {
}
