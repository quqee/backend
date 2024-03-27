package org.hits.backend.hackaton.public_interface.auth;

public record LoginDto(
        String email,
        String password
) {
}
