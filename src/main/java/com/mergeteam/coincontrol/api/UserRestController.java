package com.mergeteam.coincontrol.api;

import com.mergeteam.coincontrol.dto.user.CreateUserDto;
import com.mergeteam.coincontrol.dto.user.ReadUserDto;
import com.mergeteam.coincontrol.dto.user.UpdateUserDto;
import com.mergeteam.coincontrol.dto.wallet.WalletDto;
import com.mergeteam.coincontrol.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{id}/wallets")
    public Page<WalletDto> getAllWalletsByUserId(@PathVariable UUID id, Pageable pageable) {
        return userService.getWalletsForUser(id, pageable);
    }

    @GetMapping("/{id}")
    public ReadUserDto getUser(@PathVariable UUID id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public ReadUserDto updateUser(@PathVariable UUID id, @Validated UpdateUserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
    }

    @PostMapping("/id")
    public ReadUserDto saveUser(@Validated @RequestBody CreateUserDto createUserDto) {
        return userService.save(createUserDto);
    }

}
