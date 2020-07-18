package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.commons.clientEmail
import com.kpk.cinematickets.tickets.models.GroupTicketPurchaseRequest
import com.kpk.cinematickets.tickets.models.PurchasedGroupTicket
import com.kpk.cinematickets.tickets.models.Ticket
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tickets")
class TicketsController(
        val ticketsService: TicketsService,
        val ticketsRepository: TicketsRepository
) {

    @PostMapping("/buy")
    fun buyTickets(@RequestBody request: GroupTicketPurchaseRequest, authentication: OAuth2AuthenticationToken): PurchasedGroupTicket {
        val purchasedTicket = ticketsService.processGroupTicketPurchase(request.screeningId, request.seatIds, authentication.name)
        ticketsService.sendTicketAsync(purchasedTicket, authentication.clientEmail())
        return purchasedTicket
    }

    @GetMapping("/get")
    fun getClientTickets(authentication: OAuth2AuthenticationToken): List<Ticket> {
        return ticketsRepository.getTickets(authentication.name)
    }

}
