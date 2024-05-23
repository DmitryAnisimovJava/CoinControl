package com.mergeteam.coincontrol.api;

import com.mergeteam.coincontrol.api.sort.TransactionSort;
import com.mergeteam.coincontrol.dto.transaction.ReadExpenseTransactionDto;
import com.mergeteam.coincontrol.dto.transaction.ReadIncomeTransactionDto;
import com.mergeteam.coincontrol.response.BaseResponse;
import com.mergeteam.coincontrol.response.PageResponse;
import com.mergeteam.coincontrol.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class TransactionRestController {

    private final TransactionService transactionService;

    @GetMapping("transactions/expenses")
    public BaseResponse getAllExpenseTransactions(UUID userId, @RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit,
                                                  @RequestParam(value = "sort", defaultValue = "DATE_DESC") TransactionSort sort) {
        Page<ReadExpenseTransactionDto> transactions = transactionService.readAllWalletsExpensesByUserId(userId,
                                                                                                         PageRequest.of(offset, limit, sort.getSortValue()));
        return transactions.isEmpty() ? new BaseResponse(false) : PageResponse.of(transactions);
    }

    @GetMapping("transactions/incomes")
    public BaseResponse getAllIncomeTransactions(UUID userId, @RequestParam("offset") Integer offset,
                                                  @RequestParam("limit") Integer limit,
                                                  @RequestParam(value = "sort", defaultValue = "DATE_DESC") TransactionSort sort) {
        Page<ReadIncomeTransactionDto> transactions = transactionService.readAllWalletsIncomesByUserId(userId,
                                                                                                       PageRequest.of(offset, limit, sort.getSortValue()));
        return transactions.isEmpty() ? new BaseResponse(false) : PageResponse.of(transactions);
    }
}
