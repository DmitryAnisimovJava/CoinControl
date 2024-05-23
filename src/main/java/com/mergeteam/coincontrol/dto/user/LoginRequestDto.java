package com.mergeteam.coincontrol.dto.user;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class LoginRequestDto {
    String email;
    String password;
}
