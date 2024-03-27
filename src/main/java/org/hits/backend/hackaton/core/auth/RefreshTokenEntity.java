package org.hits.backend.hackaton.core.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.redis.core.TimeToLive;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class RefreshTokenEntity implements Serializable {
    private String tokenId;
    private String refreshToken;
    @TimeToLive
    private long expiration;
}
