package com.mergeteam.coincontrol.api;

import com.mergeteam.coincontrol.dto.user.LoginRequestDto;
import com.mergeteam.coincontrol.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class LoginRestController {

    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody(required = false) LoginRequestDto loginDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("logout")
    public ResponseEntity logout() {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity loginHandler(HttpMessageNotReadableException e) {
        return ResponseEntity.ok().build();

    }
}