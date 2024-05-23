package com.mergeteam.coincontrol.dto.wallet;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
@Builder
public class WalletDto {

    UUID id;
    String name;
    BigDecimal balance;

}
