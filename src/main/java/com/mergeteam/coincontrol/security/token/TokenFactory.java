package com.mergeteam.coincontrol.security.token;

import org.springframework.security.core.Authentication;

public interface TokenFactory<T extends Authentication, S extends Token> {

    S create(T authentication);
}
