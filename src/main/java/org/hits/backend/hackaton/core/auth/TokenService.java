package org.hits.backend.hackaton.core.auth;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.hits.backend.hackaton.configiration.security.JwtProvider;
import org.hits.backend.hackaton.configiration.security.repository.RefreshTokenRepository;
import org.hits.backend.hackaton.public_interface.auth.TokenGenerationData;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.hits.backend.hackaton.rest.auth.v1.response.JwtResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;

    public JwtResponse createTokens(TokenGenerationData data) {
        final String accessToken = jwtProvider.generateAccessToken(data);
        final String refreshToken = jwtProvider.generateRefreshToken(data);

        final RefreshTokenEntity refreshTokenEntity = getRefreshTokenEntity(refreshToken);
        refreshTokenRepository.saveRefreshToken(refreshTokenEntity);

        return new JwtResponse(accessToken, refreshToken);
    }

    public void checkRefreshToken(String refreshToken) {
        jwtProvider.validateRefreshToken(refreshToken);

        final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
        final String tokenId = claims.getId();

        final RefreshTokenEntity oldRefreshToken = refreshTokenRepository.getRefreshTokenById(tokenId)
                .orElseThrow(() -> new ExceptionInApplication("Invalid refresh token", ExceptionType.UNAUTHORIZED));

        if (!oldRefreshToken.getRefreshToken().equals(refreshToken)) {
            throw new ExceptionInApplication("Invalid refresh token", ExceptionType.UNAUTHORIZED);
        }
    }

    public String getUserIdInRefreshToken(String refreshToken) {
        return jwtProvider.getRefreshClaims(refreshToken).getSubject();
    }

    public String getTokenIdInRefreshToken(String refreshToken) {
        return jwtProvider.getRefreshClaims(refreshToken).getId();
    }

    public void deleteRefreshToken(String tokenId) {
        refreshTokenRepository.deleteRefreshToken(tokenId);
    }

    private RefreshTokenEntity getRefreshTokenEntity(String refreshToken) {
        return new RefreshTokenEntity(
                jwtProvider.getRefreshClaims(refreshToken).getId(),
                refreshToken,
                jwtProvider.getRefreshTokenTtl()
        );
    }
}
