package com.mergeteam.coincontrol.security.rest;

import com.mergeteam.coincontrol.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RestAuthenticationConfigurer extends AbstractHttpConfigurer<RestAuthenticationConfigurer, HttpSecurity> {

    private final RestLoginAuthConverter restLoginAuthConverter;

    private final LoginService loginService;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity builder) {
        var restAuthFilter = new RestAuthenticationFilter(builder.getSharedObject(AuthenticationManager.class),
                                                          this.restLoginAuthConverter);
        restAuthFilter.setSuccessHandler((request, response, authentication) -> {
        });
        restAuthFilter.setFailureHandler(
                new AuthenticationEntryPointFailureHandler(
                        new Http403ForbiddenEntryPoint()
                )
        );
        restAuthFilter.setRequestMatcher(
                new AntPathRequestMatcher("/api/v1/login", HttpMethod.POST.name())
        );
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(loginService);
        authProvider.setPasswordEncoder(passwordEncoder);
        builder.addFilterBefore(restAuthFilter, CsrfFilter.class)
                .authenticationProvider(authProvider);
    }
}

