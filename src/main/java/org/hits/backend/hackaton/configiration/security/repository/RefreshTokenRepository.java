package org.hits.backend.hackaton.configiration.security.repository;

import org.hits.backend.hackaton.core.auth.RefreshTokenEntity;

import java.util.Optional;

public interface RefreshTokenRepository {
    void saveRefreshToken(RefreshTokenEntity entity);
    Optional<RefreshTokenEntity> getRefreshTokenById(String tokenId);
    void deleteRefreshToken(String tokenId);
}
