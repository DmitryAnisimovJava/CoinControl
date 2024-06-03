package com.mergeteam.coincontrol.security.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static com.mergeteam.coincontrol.security.token.TokenCookieSessionAuthenticationStrategy.AUTHENTICATION_COOKIE_NAME;

@RequiredArgsConstructor
@Component
public class TokenAuthConverter implements AuthenticationConverter {

    private final TokenStringConverter<Token> tokenStringConverter;

    @Override
    public Authentication convert(HttpServletRequest request) {
        if (request.getCookies() != null) {
            return Stream.of(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(AUTHENTICATION_COOKIE_NAME))
                    .findFirst()
                    .map(cookie -> {
                        Token token = this.tokenStringConverter.deserialize(cookie.getValue());
                        return new PreAuthenticatedAuthenticationToken(token, cookie.getValue());
                    })
                    .orElse(null);
        }
        return null;
    }
}
