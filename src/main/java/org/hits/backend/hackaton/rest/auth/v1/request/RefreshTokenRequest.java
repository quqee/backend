package org.hits.backend.hackaton.rest.auth.v1.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RefreshTokenRequest(
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
