package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.commons.BUSINESS_ERROR_CODE
import com.kpk.cinematickets.commons.clientEmail
import com.kpk.cinematickets.tickets.models.GroupTicketPurchaseRequest
import com.kpk.cinematickets.tickets.models.InvalidTicketException
import com.kpk.cinematickets.wallet.InsufficientClientMoney
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.invoke.MethodHandles

@RestController
@RequestMapping("/tickets")
class TicketsController(val ticketsService: TicketsService) {

    private val logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().canonicalName)

    @PostMapping("/buy")
    fun buyTickets(@RequestBody request: GroupTicketPurchaseRequest, authentication: OAuth2AuthenticationToken): ResponseEntity<Any> {
        return try {
            val purchasedTicket = ticketsService.processGroupTicketPurchase(request.screeningId, request.seatIds, authentication.name)
            ticketsService.sendTicket(purchasedTicket, authentication.clientEmail())
            ResponseEntity.ok().body(purchasedTicket)
        } catch (ex: Exception) {
            logger.error("Buy tickets error:", ex)
            when (ex) {
                is InvalidTicketException, is InsufficientClientMoney -> ResponseEntity.status(BUSINESS_ERROR_CODE).body(ex.message)
                else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error!")
            }

        }
    }

}
