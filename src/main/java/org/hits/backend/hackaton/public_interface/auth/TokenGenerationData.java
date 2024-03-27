package org.hits.backend.hackaton.public_interface.auth;

import java.util.Set;
import java.util.UUID;

public record TokenGenerationData(
        UUID userId,
        Set<String> role
) {
}
