package com.mergeteam.coincontrol.security.token;

import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Token {

    private UUID id;
    private String subject;
    private List<String> authorities;
    private Instant createdAt;
    private Instant expiresAt;
}
