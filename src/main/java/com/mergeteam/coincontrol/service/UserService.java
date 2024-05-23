package com.mergeteam.coincontrol.service;

import com.mergeteam.coincontrol.dto.user.CreateUserDto;
import com.mergeteam.coincontrol.dto.user.ReadUserDto;
import com.mergeteam.coincontrol.dto.user.UpdateUserDto;
import com.mergeteam.coincontrol.dto.wallet.WalletDto;
import com.mergeteam.coincontrol.entity.User;
import com.mergeteam.coincontrol.mapper.CreateUserDtoMapper;
import com.mergeteam.coincontrol.mapper.ReadUserDtoMapper;
import com.mergeteam.coincontrol.repository.UserRepository;
import com.mergeteam.coincontrol.util.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final CreateUserDtoMapper createUserDtoMapper;
    ReadUserDtoMapper readUserDtoMapper = ReadUserDtoMapper.INSTANCE;
    private final UserRepository userRepository;

    public ReadUserDto findById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.map(readUserDtoMapper::entityToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public ReadUserDto save(CreateUserDto createUserDto) {
        User user = createUserDtoMapper.dtoToEntity(createUserDto);
        User savedUser = userRepository.save(user);
        return readUserDtoMapper.entityToDto(savedUser);
    }

    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }

    public Page<WalletDto> getWalletsForUser(UUID id, Pageable pageable) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        List<WalletDto> walletDtos = user.getWallets()
                .stream()
                .map(wallet -> WalletDto.builder()
                        .id(wallet.getId())
                        .name(wallet.getName())
                        .balance(wallet.getBalance())
                        .build())
                .toList();
        long offset = pageable.getOffset();
        long size = Math.min(pageable.getOffset() + pageable.getPageSize(), walletDtos.size());
        List<WalletDto> sublist = walletDtos.subList((int) offset, (int) size);
        return new PageImpl<>(sublist, pageable, walletDtos.size());
    }

    public ReadUserDto updateUser(UUID id, UpdateUserDto userDto) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setEmail(userDto.getEmail());
            user.setName(userDto.getName());
            user.setAvatarPath(userDto.getAvatarPath());
            User savedUser = userRepository.save(user);
            return ReadUserDto.builder()
                    .id(savedUser.getId())
                    .avatarPath(savedUser.getAvatarPath())
                    .role(savedUser.getRole())
                    .email(savedUser.getEmail())
                    .name(savedUser.getName())
                    .build();
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(UserNotFoundException::new);
    }

}
