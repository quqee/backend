package org.hits.backend.hackaton.core.user.repository.entity;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserEntity(
        UUID id,
        String username,
        String password,
        String email,
        String fullName,
        boolean onlineStatus
) {
}
