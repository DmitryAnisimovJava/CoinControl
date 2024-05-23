package com.mergeteam.coincontrol.service;

import com.mergeteam.coincontrol.dto.user.CreateUserDto;
import com.mergeteam.coincontrol.dto.user.ReadUserDto;
import com.mergeteam.coincontrol.dto.wallet.WalletDto;
import com.mergeteam.coincontrol.entity.User;
import com.mergeteam.coincontrol.entity.Wallet;
import com.mergeteam.coincontrol.entity.enums.Role;
import com.mergeteam.coincontrol.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/*
    Unit test
 */
@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private User user;
    @Mock
    private List<Wallet> wallets;

    @Mock
    private CreateUserDto createUserDto;
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        Wallet wallet1 = new Wallet(UUID.randomUUID(), user, "Wallet1", BigDecimal.valueOf(100));
        Wallet wallet2 = new Wallet(UUID.randomUUID(), user, "Wallet2", BigDecimal.valueOf(200));
        wallets = Arrays.asList(wallet1, wallet2);
        user = new User(UUID.randomUUID(), "test@example.com", "testimya", "password", "avatar/path", Role.ADMIN,
                wallets);
    }

    @Tag("Unit")
    @Test
    public void testGetWalletsForUser() {
        when(userRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        Pageable pageable = PageRequest.of(0, 10);

        Page<WalletDto> result = userService.getWalletsForUser(user.getId(), pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()
                .size()).isEqualTo(wallets.size());
        assertThat(result.getContent()
                .get(0)
                .getName()).isEqualTo(wallets.get(0)
                .getName());
        assertThat(result.getContent()
                .get(1)
                .getName()).isEqualTo(wallets.get(1)
                .getName());
    }

    @Tag("Unit")
    @ParameterizedTest
    @MethodSource("argumentCreateUserDto")
    void testSaveUser(CreateUserDto createUserDto) {
        when(userRepository.save(any(User.class))).thenReturn(user);
        ReadUserDto saved = userService.save(createUserDto);
//        CreateUserDto build = CreateUserDto.builder()
//                .build();
        assertThat(saved).isNotNull();
        System.out.println();
    }

    public static Stream<Arguments> argumentCreateUserDto() {
        return Stream.of(Arguments.of(CreateUserDto.builder()
                .email("test@mail.ru")
                .password(")O*&HTN8wiojf432")
                .avatarPath("/ava")
                .name("ArtemTest")
                .build()));
    }

}
