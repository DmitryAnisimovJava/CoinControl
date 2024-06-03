package com.mergeteam.coincontrol.security.token;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
public class DefaultCookieTokenFactory implements TokenFactory<Authentication, Token> {

    @Value("${jwt.time-to-live}")
    private Duration tokenTtl;

    @Override
    public Token create(Authentication authentication) {
        Instant now = Instant.now();
        return Token.builder()
                .id(UUID.randomUUID())
                .subject(authentication.getName())
                .createdAt(now)
                .expiresAt(now.plus(this.tokenTtl))
                .authorities(authentication.getAuthorities().stream()
                                     .map(GrantedAuthority::getAuthority)
                                     .toList())
                .build();
    }
}
