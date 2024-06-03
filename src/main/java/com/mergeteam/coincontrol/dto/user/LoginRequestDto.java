package com.mergeteam.coincontrol.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
}
