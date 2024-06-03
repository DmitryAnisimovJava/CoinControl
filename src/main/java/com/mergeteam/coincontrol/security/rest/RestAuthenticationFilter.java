package com.mergeteam.coincontrol.security.rest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;


public class RestAuthenticationFilter extends AuthenticationFilter {
    public RestAuthenticationFilter(AuthenticationManager authenticationManager,
                                    AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
    }
}
