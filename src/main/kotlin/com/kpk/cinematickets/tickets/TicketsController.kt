package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.commons.BUSINESS_ERROR_CODE
import com.kpk.cinematickets.tickets.models.GroupTicket
import com.kpk.cinematickets.tickets.models.SeatTakenException
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tickets")
class TicketsController(val ticketsService: TicketsService) {

    @PostMapping("/buy")
    fun buyTickets(@RequestBody ticket: GroupTicket, oAuth2AuthorizedClient: OAuth2AuthorizedClient): ResponseEntity<Any> {
        return try {
            val ticketInfo = ticketsService.buyGroupTicket(ticket, oAuth2AuthorizedClient.principalName)
            //TODO send ticketInfo to buyer
            ResponseEntity.ok().body(true)
        } catch (ex: SeatTakenException) {
            ResponseEntity.status(BUSINESS_ERROR_CODE).body(ex.message)
        }
    }

}
