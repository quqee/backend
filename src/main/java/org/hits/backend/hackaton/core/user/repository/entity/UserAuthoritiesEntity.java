package org.hits.backend.hackaton.core.user.repository.entity;

import java.util.UUID;

public record UserAuthoritiesEntity(
        UUID id,
        UUID userId,
        String authority
) {
}
