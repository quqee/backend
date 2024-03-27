package org.hits.backend.hackaton.public_interface.user;

public record CreateUserDto(
        String username,
        String password,
        String email,
        String fullName
) {
}
