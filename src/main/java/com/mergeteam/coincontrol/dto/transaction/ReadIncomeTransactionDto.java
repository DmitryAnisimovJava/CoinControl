package com.mergeteam.coincontrol.dto.transaction;

import com.mergeteam.coincontrol.entity.enums.ExpenseCategory;
import com.mergeteam.coincontrol.entity.enums.IncomeCategory;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Value
@Builder
public class ReadIncomeTransactionDto {

    String walletName;
    BigDecimal amount;
    OffsetDateTime date;
    IncomeCategory category;
}
