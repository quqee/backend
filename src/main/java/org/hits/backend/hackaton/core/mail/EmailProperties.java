package org.hits.backend.hackaton.core.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "email")
public record EmailProperties(
        boolean enabled,
        String from,
        String username,
        String password,
        Smtp smtp
) {
    public record Smtp(
            String auth,
            Starttls starttls,
            String host,
            String port
    ) {
    }

    public record Starttls(
            String enable
    ) {
    }
}
