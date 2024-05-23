package com.mergeteam.coincontrol.mapper;

import com.mergeteam.coincontrol.dto.user.ReadUserDto;
import com.mergeteam.coincontrol.entity.User;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ReadUserDtoMapperTest {

    @Tag("Unit")
    @ParameterizedTest
    @MethodSource("argumentCreateUser")
    void entityToDto(User user) {
        ReadUserDto userDto = ReadUserDtoMapper.INSTANCE.entityToDto(user);
        assertAll(() -> assertThat(user.getEmail()).isEqualTo(userDto.getEmail()));
    }

    public static Stream<Arguments> argumentCreateUser() {
        return Stream.of(Arguments.of(User.builder()
                .email("test@mail.ru")
                .password(")O*&HTN8wiojf432")
                .avatarPath("/ava")
                .name("ArtemTest")
                .build()));
    }
}