package com.mergeteam.coincontrol.service;

import com.mergeteam.coincontrol.dto.transaction.ReadExpenseTransactionDto;
import com.mergeteam.coincontrol.dto.transaction.ReadIncomeTransactionDto;
import com.mergeteam.coincontrol.entity.ExpenseTransaction;
import com.mergeteam.coincontrol.entity.IncomeTransaction;
import com.mergeteam.coincontrol.entity.Wallet;
import com.mergeteam.coincontrol.mapper.ReadExpenseTransactionMapper;
import com.mergeteam.coincontrol.mapper.ReadIncomeTransactionMapper;
import com.mergeteam.coincontrol.repository.ExpenseTransactionRepository;
import com.mergeteam.coincontrol.repository.IncomeTransactionRepository;
import com.mergeteam.coincontrol.repository.WalletRepository;
import com.mergeteam.coincontrol.utils.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@Tag(TestTags.UNIT)
class TransactionServiceTest {

    @Mock
    private ExpenseTransactionRepository expenseTransactionRepository;
    @Mock
    private IncomeTransactionRepository incomeTransactionRepository;
    @Mock
    private WalletRepository walletRepository;
    @Spy
    private ReadExpenseTransactionMapper readExpenseTransactionMapper =
            Mappers.getMapper(ReadExpenseTransactionMapper.class);
    @Spy
    private ReadIncomeTransactionMapper readIncomeTransactionMapper =
            Mappers.getMapper(ReadIncomeTransactionMapper.class);

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void readAllWalletsExpensesByUserId_haveWalletsAndTransactions_returnProperPage() {
        List<Wallet> wallets = List.of(Wallet.builder().id(UUID.randomUUID()).build(),
                                       Wallet.builder().id(UUID.randomUUID()).build());
        List<ExpenseTransaction> expenseEntities = List.of(ExpenseTransaction.builder()
                                                                   .id(UUID.randomUUID())
                                                                   .amount(BigDecimal.valueOf(100))
                                                                   .build(),
                                                           ExpenseTransaction.builder()
                                                                   .id(UUID.randomUUID())
                                                                   .amount(BigDecimal.valueOf(200))
                                                                   .build());
        doReturn(wallets).when(walletRepository).findAllByUserId(any());
        doReturn(new PageImpl<ExpenseTransaction>(expenseEntities)).when(expenseTransactionRepository)
                .findAllByWalletsId(any(), any());
        Page<ReadExpenseTransactionDto> allExpensesForUser =
                transactionService.readAllWalletsExpensesByUserId(UUID.randomUUID(), Pageable.unpaged());
        assertAll(() -> assertThat(allExpensesForUser).isNotEmpty(),
                  () -> assertThat(allExpensesForUser).hasSize(2),
                  () -> assertThat(allExpensesForUser)
                          .anyMatch(expense -> expense.getAmount().equals(BigDecimal.valueOf(100)))
                          .anyMatch(expense -> expense.getAmount().equals(BigDecimal.valueOf(200))));

    }

    @Test
    void readAllWalletsExpensesByUserId_noWallets_throw404Exception() {
        doReturn(Collections.EMPTY_LIST).when(walletRepository).findAllByUserId(any());
        ResponseStatusException responseStatusException = catchThrowableOfType(
                () -> transactionService.readAllWalletsExpensesByUserId(UUID.randomUUID(), Pageable.unpaged()),
                ResponseStatusException.class);
        assertAll(() -> assertThat(responseStatusException).isNotNull(),
                  () -> assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
    }

    @Test
    void readAllWalletsIncomesByUserId() {
        List<Wallet> wallets = List.of(Wallet.builder().id(UUID.randomUUID()).build(),
                                       Wallet.builder().id(UUID.randomUUID()).build());
        List<IncomeTransaction> incomeEntities = List.of(IncomeTransaction.builder()
                                                                   .id(UUID.randomUUID())
                                                                   .amount(BigDecimal.valueOf(100))
                                                                   .build(),
                                                          IncomeTransaction.builder()
                                                                   .id(UUID.randomUUID())
                                                                   .amount(BigDecimal.valueOf(200))
                                                                   .build());
        doReturn(wallets).when(walletRepository).findAllByUserId(any());
        doReturn(new PageImpl<IncomeTransaction>(incomeEntities)).when(incomeTransactionRepository)
                .findAllByWalletsId(any(), any());
        Page<ReadIncomeTransactionDto> allExpensesForUser =
                transactionService.readAllWalletsIncomesByUserId(UUID.randomUUID(), Pageable.unpaged());
        assertAll(() -> assertThat(allExpensesForUser).isNotEmpty(),
                  () -> assertThat(allExpensesForUser).hasSize(2),
                  () -> assertThat(allExpensesForUser)
                          .anyMatch(expense -> expense.getAmount().equals(BigDecimal.valueOf(100)))
                          .anyMatch(expense -> expense.getAmount().equals(BigDecimal.valueOf(200))));
    }

    @Test
    void readAllWalletsIncomeByUserId_noWallets_throw404Exception() {
        doReturn(Collections.EMPTY_LIST).when(walletRepository).findAllByUserId(any());
        ResponseStatusException responseStatusException = catchThrowableOfType(
                () -> transactionService.readAllWalletsIncomesByUserId(UUID.randomUUID(), Pageable.unpaged()),
                ResponseStatusException.class);
        assertAll(() -> assertThat(responseStatusException).isNotNull(),
                  () -> assertThat(responseStatusException.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND));
    }

}