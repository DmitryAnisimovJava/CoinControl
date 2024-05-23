package com.mergeteam.coincontrol.dto.user;

import com.mergeteam.coincontrol.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDto {

    @Email
    String email;
    String password;
    String avatarPath;
    @NotNull
    String name;
    @NotNull
    Role role;

}
