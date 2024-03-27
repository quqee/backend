package org.hits.backend.hackaton.configiration.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.NonNull;
import org.hits.backend.hackaton.core.user.repository.entity.UserEntity;
import org.hits.backend.hackaton.public_interface.auth.TokenGenerationData;
import org.hits.backend.hackaton.public_interface.exception.ExceptionInApplication;
import org.hits.backend.hackaton.public_interface.exception.ExceptionType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Getter
@Service
public class JwtProvider {
    private static final String ROLE = "role";
    private final SecretKey jwtAccessSecret;
    private final long jwtAccessTtlSecond;
    private final SecretKey jwtRefreshSecret;
    private final long jwtRefreshTtlSecond;

    public JwtProvider(
            @Value("${jwt.token.access.secret}") String jwtAccessSecret,
            @Value("${jwt.token.refresh.secret}") String jwtRefreshSecret,
            @Value("${jwt.token.access.expiration}") Long jwtAccessTtlSecond,
            @Value("${jwt.token.refresh.expiration}") Long jwtRefreshTtlSecond
    ) {
        this.jwtAccessSecret =  Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtAccessTtlSecond = jwtAccessTtlSecond;
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
        this.jwtRefreshTtlSecond = jwtRefreshTtlSecond;
    }

    public String generateAccessToken(@NonNull TokenGenerationData data) {
        final Date accessExpiration = getDateWithPlus(jwtAccessTtlSecond);
        final String accessTokenId = UUID.randomUUID().toString();
        return Jwts.builder()
                .subject(String.valueOf(data.userId()))
                .claim(ROLE, data.role())
                .expiration(accessExpiration)
                .id(accessTokenId)
                .signWith(jwtAccessSecret)
                .compact();
    }

    public String generateRefreshToken(@NonNull TokenGenerationData data) {
        final Date refreshExpiration = getDateWithPlus(jwtRefreshTtlSecond);
        final String refreshTokenId = UUID.randomUUID().toString();
        return Jwts.builder()
                .subject(String.valueOf(data.userId()))
                .expiration(refreshExpiration)
                .id(refreshTokenId)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    public void validateRefreshToken(@NonNull String refreshToken) {
        validateToken(refreshToken, jwtRefreshSecret);
    }

    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    public long getRefreshTokenTtl() {
        return jwtRefreshTtlSecond;
    }

    public String extractUserIdFromAccessToken(@NonNull String accessToken) {
        return getAccessClaims(accessToken).getSubject();
    }

    public boolean isAccessTokenValid(@NonNull String accessToken, UserEntity user) {
        validateToken(accessToken, jwtAccessSecret);
        var claims = getAccessClaims(accessToken);
        return user.id().toString().equals(claims.getSubject());
    }

    private Claims getClaims(@NonNull String token, @NonNull SecretKey secret) {
        return Jwts.parser()
                .verifyWith(secret)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void validateToken(@NonNull String token, @NonNull SecretKey secret) {
        try {
            Jwts.parser()
                    .verifyWith(secret)
                    .build()
                    .parse(token);
        } catch (ExpiredJwtException expEx) {
            throw new ExceptionInApplication("Token expired", ExceptionType.UNAUTHORIZED);
        } catch (UnsupportedJwtException unsEx) {
            throw new ExceptionInApplication("Unsupported token", ExceptionType.UNAUTHORIZED);
        } catch (MalformedJwtException mjEx) {
            throw new ExceptionInApplication("Malformed token", ExceptionType.UNAUTHORIZED);
        } catch (Exception e) {
            throw new ExceptionInApplication("Invalid token", ExceptionType.UNAUTHORIZED);
        }
    }

    private Date getDateWithPlus(long delta) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusSeconds(delta)
                .atZone(ZoneId.systemDefault())
                .toInstant();
        return Date.from(refreshExpirationInstant);
    }

}