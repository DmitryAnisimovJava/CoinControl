package com.mergeteam.coincontrol.dto.user;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateUserDto {

    @Email
    String email;
    String name;
    String avatarPath;

}
