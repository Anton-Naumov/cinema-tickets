package com.kpk.cinematickets.wallet

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class WalletService(val walletRepository: WalletRepository) {

    @Transactional
    fun makePayment(clientName: String, payment: BigDecimal) {
        val currentClientMoney = walletRepository.getClientMoneyForUpdate(clientName)
        if (currentClientMoney < payment) {
            throw InsufficientClientMoney("Client $clientName can't pay $payment, he has only: $currentClientMoney")
        }
        walletRepository.updateClientMoneyAmount(clientName, currentClientMoney.minus(payment))
    }

    @Transactional
    fun makeDeposit(clientName: String, deposit: BigDecimal) {
        val currentClientMoney = walletRepository.getClientMoneyForUpdate(clientName)
        walletRepository.updateClientMoneyAmount(clientName, currentClientMoney.plus(deposit))
    }
    
}
