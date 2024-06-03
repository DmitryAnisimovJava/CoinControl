package com.mergeteam.coincontrol.security.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mergeteam.coincontrol.dto.user.LoginRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestLoginAuthConverter implements AuthenticationConverter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication convert(HttpServletRequest request) {
        try {
            LoginRequestDto loginRequestDto = this.objectMapper.readValue(request.getInputStream(),
                                                                          LoginRequestDto.class);
            return new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                                                           loginRequestDto.getPassword());
        } catch (IOException e) {
            return null;
        }
    }
}
