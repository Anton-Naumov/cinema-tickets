package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.movies.MoviesRepository
import com.kpk.cinematickets.movies.models.Seat
import com.kpk.cinematickets.notifications.NotificationService
import com.kpk.cinematickets.tickets.models.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.StringBuilder
import java.time.LocalDateTime
import java.util.*

interface TicketsService {
    fun processGroupTicketPurchase(screeningId: Long, seats: List<Seat>, buyerName: String): PurchasedGroupTicket
    fun sendTicket(ticketInfo: PurchasedGroupTicket, receiverEmail: String)
}

@Service
class TicketsServiceImpl(
        val ticketsRepository: TicketsRepository,
        val moviesRepository: MoviesRepository,
        val notificationService: NotificationService
): TicketsService {

    @Throws(TicketPurchaseException::class)
    @Transactional
    override fun processGroupTicketPurchase(screeningId: Long, seats: List<Seat>, buyerName: String): PurchasedGroupTicket {
        val screeningWithMovie = moviesRepository.getScreeningWithMovie(screeningId)
        if (screeningWithMovie == null || screeningWithMovie.screening.time < LocalDateTime.now()) {
            throw InvalidScreeningException("Screening is invalid!")
        }
        return PurchasedGroupTicket(screeningWithMovie, saveTickets(screeningId, seats, buyerName))
    }

    private fun saveTickets(screeningId: Long, seats: List<Seat>, buyerName: String): List<PurchasedSeat> {
        return seats.map { seat ->
            val uniqueTicketId = generateNewTicketUniqueId()
            ticketsRepository.insertTicket(screeningId, seat.id, uniqueTicketId, buyerName)
            PurchasedSeat(uniqueTicketId, seat.number)
        }
    }

    private fun generateNewTicketUniqueId(): String {
        return UUID.randomUUID().toString()
    }

    override fun sendTicket(ticketInfo: PurchasedGroupTicket, receiverEmail: String) {
        notificationService.sendNotification(
                receiverEmail,
                "Ticket for ${ticketInfo.screeningWithMovie.movie.title}",
                ticketInfo.getDetailsString()
        )
    }

}
