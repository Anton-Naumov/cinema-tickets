package com.kpk.cinematickets.wallet

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

@RestController
@RequestMapping("/wallet")
class ClientWalletController(
        val walletRepository: WalletRepository,
        val walletService: WalletService
) {

    @GetMapping("/get-balance")
    fun getBalance(authentication: OAuth2AuthenticationToken): BigDecimal {
        return walletRepository.getClientMoney(authentication.name)
    }

    @PostMapping("/deposit")
    fun depositMoney(@RequestBody deposit: BigDecimal, authentication: OAuth2AuthenticationToken): ResponseEntity<Any> {
        if (deposit > BigDecimal.ZERO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Deposit can not be negative")
        }
        walletService.makeDeposit(authentication.name, deposit)
        return ResponseEntity.ok().body(true)
    }

}
