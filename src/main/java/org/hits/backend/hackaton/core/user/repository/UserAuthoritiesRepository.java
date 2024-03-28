package org.hits.backend.hackaton.core.user.repository;

import java.util.UUID;

public interface UserAuthoritiesRepository {
    void deleteByUserId(UUID userId);
}
