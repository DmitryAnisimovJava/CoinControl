package com.mergeteam.coincontrol.api;

import com.mergeteam.coincontrol.dto.wallet.WalletDto;
import com.mergeteam.coincontrol.entity.Wallet;
import com.mergeteam.coincontrol.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletRestController {

    private final WalletService walletService;

    @PostMapping()
    @ResponseStatus(HttpStatus.OK)
    private Wallet createWallet(@RequestBody WalletDto walletDto, UUID userId) {
        return walletService.createWallet(walletDto, userId);
    }

    @DeleteMapping("/{walletId}")
    @ResponseStatus(HttpStatus.OK)
    private Optional<Wallet> deleteWallet(@RequestParam UUID walletId) {
        return walletService.deleteWalletByWalletId(walletId);
    }
    @GetMapping("/balance/{walletId}")
    public BigDecimal getBalance(@PathVariable UUID walletId) {
        return walletService.getBalance(walletId);
    }
}

// GET /balance
// POST /transaction
// GET /transactions:
// GET /income
// GET /expense
// DELETE {id}



