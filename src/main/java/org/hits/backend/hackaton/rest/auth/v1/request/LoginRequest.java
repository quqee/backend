package org.hits.backend.hackaton.rest.auth.v1.request;

public record LoginRequest(
        String email,
        String password
) {
}
