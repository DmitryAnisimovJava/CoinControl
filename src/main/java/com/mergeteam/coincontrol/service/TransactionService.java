package com.mergeteam.coincontrol.service;

import com.mergeteam.coincontrol.dto.transaction.ReadExpenseTransactionDto;
import com.mergeteam.coincontrol.dto.transaction.ReadIncomeTransactionDto;
import com.mergeteam.coincontrol.entity.Wallet;
import com.mergeteam.coincontrol.mapper.ReadExpenseTransactionMapper;
import com.mergeteam.coincontrol.mapper.ReadIncomeTransactionMapper;
import com.mergeteam.coincontrol.repository.ExpenseTransactionRepository;
import com.mergeteam.coincontrol.repository.IncomeTransactionRepository;
import com.mergeteam.coincontrol.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TransactionService {

    private final ExpenseTransactionRepository expenseTransactionRepository;
    private final IncomeTransactionRepository incomeTransactionRepository;
    private final WalletRepository walletRepository;
    private final ReadExpenseTransactionMapper readExpenseTransactionMapper;
    private final ReadIncomeTransactionMapper readIncomeTransactionMapper;

    public Page<ReadExpenseTransactionDto> readAllWalletsExpensesByUserId(UUID userId, Pageable pageable) {
        List<UUID> allWallets = walletRepository.findAllByUserId(userId).stream().map(Wallet::getId)
                .toList();
        if (allWallets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return expenseTransactionRepository.findAllByWalletsId(allWallets, pageable)
                .map(readExpenseTransactionMapper::entityToDto);
    }

    public Page<ReadIncomeTransactionDto> readAllWalletsIncomesByUserId(UUID userId, Pageable pageable) {
        List<UUID> allWallets = walletRepository.findAllByUserId(userId).stream().map(Wallet::getId)
                .toList();
        if (allWallets.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return incomeTransactionRepository.findAllByWalletsId(allWallets, pageable)
                .map(readIncomeTransactionMapper::entityToDto);
    }
}
