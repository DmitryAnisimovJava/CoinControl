package com.mergeteam.coincontrol.dto.transaction;

import com.mergeteam.coincontrol.entity.enums.ExpenseCategory;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
@Builder
public class ReadExpenseTransactionDto {

    String walletName;
    BigDecimal amount;
    OffsetDateTime date;
    ExpenseCategory category;

}
