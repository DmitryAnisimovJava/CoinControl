package com.mergeteam.coincontrol.mapper;

import com.mergeteam.coincontrol.dto.user.CreateUserDto;
import com.mergeteam.coincontrol.entity.User;
import com.mergeteam.coincontrol.integration.IntegrationBaseClass;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Маппер можно протестировать только в интеграционном варианте, так как есть @Inject внутри маппера, а значит нужно
 * обязательно создать ApplicationContext. В Unit варианте тест не работает. Нужно обдумать решение.
 * Mappers.getMapper() тоже не работает, так как inject не сработает.
 */
@RequiredArgsConstructor
public class CreateUserDtoMapperTest extends IntegrationBaseClass {

    private final CreateUserDtoMapper createUserDtoMapper;

    @ParameterizedTest
    @MethodSource("argumentCreateUser")
    void entityToDto(CreateUserDto user) {
        User userDto = createUserDtoMapper.dtoToEntity(user);
        assertAll(() -> assertThat(user.getEmail()).isEqualTo(userDto.getEmail()));
    }

    public static List<CreateUserDto> argumentCreateUser() {
        return List.of(CreateUserDto.builder()
                               .email("test@mail.ru")
                               .password("123")
                               .avatarPath("/ava")
                               .name("ArtemTest")
                               .build());
    }
}