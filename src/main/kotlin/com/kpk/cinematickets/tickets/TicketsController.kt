package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.commons.clientEmail
import com.kpk.cinematickets.tickets.models.GroupTicketPurchaseRequest
import com.kpk.cinematickets.tickets.models.PurchasedGroupTicket
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketsController(val ticketsService: TicketsService) {

    @PostMapping("/buy")
    fun buyTickets(@RequestBody request: GroupTicketPurchaseRequest, authentication: OAuth2AuthenticationToken): PurchasedGroupTicket {
        val purchasedTicket = ticketsService.processGroupTicketPurchase(request.screeningId, request.seatIds, authentication.name)
        ticketsService.sendTicketAsync(purchasedTicket, authentication.clientEmail())
        return purchasedTicket
    }

}
