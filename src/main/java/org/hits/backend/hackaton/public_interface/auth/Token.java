package org.hits.backend.hackaton.public_interface.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Token {
    private int expiration;
    private String secret;
}
