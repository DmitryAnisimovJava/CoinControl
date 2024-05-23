package com.mergeteam.coincontrol.integration.service;

import com.mergeteam.coincontrol.dto.transaction.ReadExpenseTransactionDto;
import com.mergeteam.coincontrol.dto.transaction.ReadIncomeTransactionDto;
import com.mergeteam.coincontrol.integration.IntegrationBaseClass;
import com.mergeteam.coincontrol.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@RequiredArgsConstructor
class TransactionServiceIT extends IntegrationBaseClass {

    private final TransactionService transactionService;
    private static final UUID userId = UUID.fromString("e0ee31f6-6c9b-4987-8fba-246b059b3bee");

    @Test
    void readAllWalletsExpensesByUserId() {
        Page<ReadExpenseTransactionDto> expenses = transactionService.readAllWalletsExpensesByUserId(userId,
                                                                                                     Pageable.unpaged());
        assertAll(() -> assertThat(expenses).isNotEmpty(),
                  () -> assertThat(expenses).hasSize(2),
                  () -> assertThat(expenses).satisfiesExactly(
                          expense1 -> {
                              assertThat(expense1.getAmount().longValue()).isEqualTo(50);
                              assertThat(expense1.getWalletName()).isEqualTo("Main wallet");
                          },
                          expense2 -> {
                              assertThat(expense2.getAmount().longValue()).isEqualTo(45);
                              assertThat(expense2.getWalletName()).isEqualTo("Main wallet");
                          }));
    }

    @Test
    void readAllWalletsIncomesByUserId() {
        Page<ReadIncomeTransactionDto> incomes =
                transactionService.readAllWalletsIncomesByUserId(userId, Pageable.unpaged());
        assertAll(() -> assertThat(incomes).isNotEmpty(),
                  () -> assertThat(incomes).hasSize(2),
                  () -> assertThat(incomes).satisfiesExactly(
                          income -> {
                              assertThat(income.getAmount().longValue()).isEqualTo(100);
                              assertThat(income.getWalletName()).isEqualTo("Main wallet");
                          },
                          income2 -> {
                              assertThat(income2.getAmount().longValue()).isEqualTo(200);
                              assertThat(income2.getWalletName()).isEqualTo("Second wallet");
                          }));



    }
}