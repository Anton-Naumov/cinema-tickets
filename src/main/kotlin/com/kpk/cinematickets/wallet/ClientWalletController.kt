package com.kpk.cinematickets.wallet

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/wallet")
class ClientWalletController(
        val walletRepository: WalletRepository,
        val walletService: WalletService
) {

    @GetMapping("/get-money")
    fun getMoney(authentication: OAuth2AuthenticationToken): BigDecimal {
        return walletRepository.getClientMoney(authentication.name)
    }

    @PostMapping("/deposit")
    fun depositMoney(@RequestBody deposit: BigDecimal, authentication: OAuth2AuthenticationToken) {
        return walletService.makeDeposit(authentication.name, deposit)
    }

}
