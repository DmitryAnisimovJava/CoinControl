package com.mergeteam.coincontrol.security.token;

import com.mergeteam.coincontrol.entity.ExpiredToken;
import com.mergeteam.coincontrol.repository.ExpiredTokenRepository;
import com.mergeteam.coincontrol.security.rest.RestLogountSuccessHandler;
import com.mergeteam.coincontrol.service.TokenAuthUserDetailsService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
@RequiredArgsConstructor
public class TokenCookieAuthenticationConfigurer
        extends AbstractHttpConfigurer<TokenCookieAuthenticationConfigurer, HttpSecurity> {

    private final TokenAuthConverter tokenAuthConverter;

    private final TokenAuthUserDetailsService tokenAuthUserDetailsService;

    private final ExpiredTokenRepository expiredTokenRepository;

    private static final String COOKIE_NAME = TokenCookieSessionAuthenticationStrategy.AUTHENTICATION_COOKIE_NAME;
    @Override
    public void init(HttpSecurity builder) throws Exception {
        builder.logout(logoutConfig -> logoutConfig.logoutUrl("/api/v1/logout")
                .addLogoutHandler(new CookieClearingLogoutHandler(COOKIE_NAME))
                .addLogoutHandler((request, response, authentication) -> {
                    if (authentication != null && authentication.getPrincipal() instanceof TokenUser user) {
                        expiredTokenRepository.save(ExpiredToken.builder()
                                                            .id(user.getToken().getId())
                                                            .keepUntil(LocalDateTime.ofInstant(user.getToken()
                                                                                                       .getExpiresAt(),
                                                                                               ZoneOffset.UTC))
                                                            .build());
                        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    }
                })
                .logoutSuccessHandler(new RestLogountSuccessHandler()));
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        var cookieAuthFilter = new AuthenticationFilter(builder.getSharedObject(AuthenticationManager.class),
                                                        this.tokenAuthConverter);
        cookieAuthFilter.setSuccessHandler((request, response, authentication) -> {});
        cookieAuthFilter.setFailureHandler(
                new AuthenticationEntryPointFailureHandler(
                        new Http403ForbiddenEntryPoint()
                )
        );

        var authProvider = new PreAuthenticatedAuthenticationProvider();
        authProvider.setPreAuthenticatedUserDetailsService(tokenAuthUserDetailsService);
        builder.addFilterAfter(cookieAuthFilter, CsrfFilter.class)
                .authenticationProvider(authProvider);
    }
}
