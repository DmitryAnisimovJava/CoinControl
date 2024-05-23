package com.mergeteam.coincontrol.dto.user;

import com.mergeteam.coincontrol.entity.enums.Role;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ReadUserDto {

    UUID id;
    String email;
    String name;
    String avatarPath;
    Role role;

}
