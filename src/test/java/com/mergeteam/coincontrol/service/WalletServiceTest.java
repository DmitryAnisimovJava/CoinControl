package com.mergeteam.coincontrol.service;

import com.mergeteam.coincontrol.entity.User;
import com.mergeteam.coincontrol.entity.Wallet;
import com.mergeteam.coincontrol.repository.ExpenseTransactionRepository;
import com.mergeteam.coincontrol.repository.IncomeTransactionRepository;
import com.mergeteam.coincontrol.repository.UserRepository;
import com.mergeteam.coincontrol.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private ExpenseTransactionRepository expenseTransactionRepository;
    @Mock
    private IncomeTransactionRepository incomeTransactionRepository;
    @InjectMocks
    private WalletService walletService;

//    @Test
//    void createWallet() {
//        // given
//        User user = userRepository.save(User.builder()
//                .email("test@test.com")
//                .name("Test User")
//                .password("password")
//                .build());
//        WalletDto walletDto = WalletDto.builder()
//                .name("Test Wallet")
//                .balance(BigDecimal.valueOf(100.0))
//                .build();
//
//        // when
//        Wallet wallet = walletService.createWallet(walletDto);
//
//        // then
//        assertThat(wallet).isNotNull();
//        assertThat(wallet.getId()).isNotNull();
//        assertThat(wallet.getUser()).isEqualTo(user);
//        assertThat(wallet.getBalance()).isEqualTo(100.0);
//    }

    @Test
    void deleteWalletByWalletId() {
    }

    @Test
    void testGetBalance() {



        User user = userRepository.save(User.builder()
                .email("test@test.com")
                .name("Test User")
                .password("password")
                .build());
        Wallet wallet = walletRepository.save(Wallet.builder()
                .user(user)
                .balance(BigDecimal.valueOf(100.0))
                .build());

        // when
        BigDecimal balance = walletService.getBalance(wallet.getId());

        assertThat(balance).isEqualTo(BigDecimal.valueOf(100.0));


    }
}