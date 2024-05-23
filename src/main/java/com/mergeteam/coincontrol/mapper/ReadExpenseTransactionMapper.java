package com.mergeteam.coincontrol.mapper;

import com.mergeteam.coincontrol.dto.transaction.ReadExpenseTransactionDto;
import com.mergeteam.coincontrol.entity.ExpenseTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface ReadExpenseTransactionMapper {

    @Mappings({
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "date", source = "date"),
            @Mapping(target = "category", source = "category"),
            @Mapping(target = "walletName", source = "walletId.name"),
    })
    ReadExpenseTransactionDto entityToDto(ExpenseTransaction expenseTransaction);

}
