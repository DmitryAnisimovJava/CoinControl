package com.mergeteam.coincontrol.mapper;

import com.mergeteam.coincontrol.dto.ReadExpenseTransactionDto;
import com.mergeteam.coincontrol.dto.ReadIncomeTransactionDto;
import com.mergeteam.coincontrol.entity.ExpenseTransaction;
import com.mergeteam.coincontrol.entity.IncomeTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",nullValueCheckStrategy = NullValueCheckStrategy.ON_IMPLICIT_CONVERSION)
public interface ReadIncomeTransactionMapper {


    @Mappings({
            @Mapping(target = "amount", source = "amount"),
            @Mapping(target = "date", source = "date"),
            @Mapping(target = "category", source = "category"),
            @Mapping(target = "walletName", source = "walletId.name"),
    })
    ReadIncomeTransactionDto entityToDto(IncomeTransaction expenseTransaction);

}
