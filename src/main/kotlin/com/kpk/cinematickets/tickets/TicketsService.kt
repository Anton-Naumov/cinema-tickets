package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.tickets.models.GroupTicket
import com.kpk.cinematickets.tickets.models.SeatTakenException
import com.kpk.cinematickets.tickets.models.TicketsInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TicketsService(val ticketsRepository: TicketsRepository) {

    @Throws(SeatTakenException::class)
    @Transactional
    fun buyGroupTicket(ticket: GroupTicket, buyerEmail: String): TicketsInfo {
        return ticket.seatNumbers.map { seatNumber ->
            val uniqueTicketId = UUID.randomUUID().toString()
            ticketsRepository.insertTicket(ticket.screeningId, seatNumber, uniqueTicketId, buyerEmail)
            uniqueTicketId
        }.let { TicketsInfo(it) }
    }

}
