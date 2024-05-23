package com.mergeteam.coincontrol.mapper;

import com.mergeteam.coincontrol.dto.transaction.ReadIncomeTransactionDto;
import com.mergeteam.coincontrol.entity.IncomeTransaction;
import com.mergeteam.coincontrol.entity.Wallet;
import com.mergeteam.coincontrol.entity.enums.IncomeCategory;
import com.mergeteam.coincontrol.utils.TestTags;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
@ExtendWith(MockitoExtension.class)
@Tag(TestTags.UNIT)
class ReadIncomeTransactionMapperTest {

    @Spy
    private ReadIncomeTransactionMapper readIncomeTransactionMapper = Mappers.getMapper(ReadIncomeTransactionMapper.class);
    @Test
    void dtoToEntity() {
        IncomeTransaction expenseTransaction = IncomeTransaction.builder()
                .date(OffsetDateTime.now())
                .amount(BigDecimal.valueOf(1022.3422))
                .walletId(Wallet.builder().name("nobyWallet").build())
                .category(IncomeCategory.HOBBY).build();
        ReadIncomeTransactionDto readExpenseTransactionDto = readIncomeTransactionMapper.entityToDto(expenseTransaction);
        assertAll(() -> assertThat(expenseTransaction.getCategory()).isEqualTo(readExpenseTransactionDto.getCategory()),
                  () -> assertThat(expenseTransaction.getAmount()).isEqualTo(readExpenseTransactionDto.getAmount()),
                  () -> assertThat(expenseTransaction.getDate()).isEqualTo(readExpenseTransactionDto.getDate()),
                  () -> assertThat(expenseTransaction.getWalletId().getName()).isEqualTo(readExpenseTransactionDto.getWalletName()));
    }

}
