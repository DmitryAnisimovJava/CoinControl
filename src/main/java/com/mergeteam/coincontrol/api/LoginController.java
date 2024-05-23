package com.mergeteam.coincontrol.api;

import com.mergeteam.coincontrol.dto.user.LoginRequestDto;
import com.mergeteam.coincontrol.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class LoginController {


    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Validated LoginRequestDto loginDto) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                                                                                       loginDto.getPassword()));
        return ResponseEntity.ok().build();

    }


}
