package com.mergeteam.coincontrol.security.token;

import com.mergeteam.coincontrol.security.token.DefaultCookieTokenFactory;
import com.mergeteam.coincontrol.security.token.Token;
import com.mergeteam.coincontrol.security.token.TokenStringConverter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Component
public class TokenCookieSessionAuthenticationStrategy implements SessionAuthenticationStrategy {

    private final TokenFactory<Authentication, Token> tokenCookieFactory;
    private final TokenStringConverter<Token> tokenStringConverter;
    public static final String AUTHENTICATION_COOKIE_NAME = "auth-token";

    @Override
    public void onAuthentication(Authentication authentication, HttpServletRequest request,
                                 HttpServletResponse response) throws SessionAuthenticationException {

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            Token token = this.tokenCookieFactory.create(authentication);
            String tokenString = this.tokenStringConverter.serialize(token);
            Cookie cookie = new Cookie(AUTHENTICATION_COOKIE_NAME, tokenString);
            cookie.setPath("/");
            cookie.setDomain(null);
//            cookie.setSecure(true); Need to create Https connection
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int) ChronoUnit.SECONDS.between(Instant.now(), token.getExpiresAt()));
            response.addCookie(cookie);
        }
    }
}
