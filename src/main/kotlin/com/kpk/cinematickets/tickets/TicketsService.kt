package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.movies.MoviesRepository
import com.kpk.cinematickets.tickets.models.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.util.*

@Service
class TicketsService(
        val ticketsRepository: TicketsRepository,
        val moviesRepository: MoviesRepository
) {

    @Throws(TicketPurchaseException::class)
    @Transactional
    fun processGroupTicketPurchase(screeningId: Long, seatIds: List<Long>, buyerName: String): PurchasedGroupTicket {
        val screeningWithMovie = moviesRepository.getScreeningWithMovie(screeningId)
        if (screeningWithMovie.screening.time < LocalDateTime.now()) {
            throw InvalidScreeningException("Screening time passed!")
        }
        return seatIds.map { seatId ->
            val uniqueTicketId = generateNewTicketUniqueId()
            ticketsRepository.insertTicket(screeningId, seatId, uniqueTicketId, buyerName)
            uniqueTicketId
        }.let { PurchasedGroupTicket(screeningWithMovie, it) }
    }

    private fun generateNewTicketUniqueId(): String {
        return UUID.randomUUID().toString()
    }

}
