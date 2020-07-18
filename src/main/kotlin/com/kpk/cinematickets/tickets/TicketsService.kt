package com.kpk.cinematickets.tickets

import com.kpk.cinematickets.theater.TheaterRepository
import com.kpk.cinematickets.theater.models.Seat
import com.kpk.cinematickets.notifications.NotificationService
import com.kpk.cinematickets.tickets.models.*
import com.kpk.cinematickets.wallet.InsufficientClientMoney
import com.kpk.cinematickets.wallet.WalletService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

interface TicketsService {
    fun processGroupTicketPurchase(screeningId: Long, seatIdsToPurchase: List<Long>, buyerName: String): PurchasedGroupTicket
    fun sendTicketAsync(ticketInfo: PurchasedGroupTicket, receiverEmail: String)
}

@Service
class TicketsServiceImpl(
        val ticketsRepository: TicketsRepository,
        val theaterRepository: TheaterRepository,
        val notificationService: NotificationService,
        val walletService: WalletService
): TicketsService {

    @Throws(InvalidTicketException::class, InsufficientClientMoney::class)
    @Transactional
    override fun processGroupTicketPurchase(screeningId: Long, seatIdsToPurchase: List<Long>, buyerName: String): PurchasedGroupTicket {
        val movieScreening = theaterRepository.getScreeningWithMovie(screeningId)
        if (movieScreening == null || movieScreening.screening.time < LocalDateTime.now()) {
            throw InvalidScreeningException("Invalid screening!")
        }

        val ticketSeats = theaterRepository.getScreeningSeats(screeningId).filter { seatIdsToPurchase.contains(it.id) }
        if (ticketSeats.size != seatIdsToPurchase.size || ticketSeats.any{ !it.isFree }) {
            throw InvalidSeatsException("Invalid seat ids!")
        }

        processClientPayment(buyerName, movieScreening.screening.price, ticketSeats.size)
        return PurchasedGroupTicket(movieScreening, saveTickets(screeningId, ticketSeats, buyerName))
    }

    private fun saveTickets(screeningId: Long, seats: List<Seat>, buyerName: String): List<PurchasedSeat> {
        return seats.map { seat ->
            val uniqueTicketId = generateNewTicketUniqueId()
            ticketsRepository.insertTicket(screeningId, seat.id!!, uniqueTicketId, buyerName)
            PurchasedSeat(uniqueTicketId, seat.number)
        }
    }

    private fun generateNewTicketUniqueId(): String {
        return UUID.randomUUID().toString()
    }

    private fun processClientPayment(clientName: String, screeningPrice: BigDecimal, ticketsCount: Int) {
        walletService.makePayment(clientName, screeningPrice.multiply(BigDecimal(ticketsCount)))
    }

    override fun sendTicketAsync(ticketInfo: PurchasedGroupTicket, receiverEmail: String) {
        GlobalScope.launch {
            notificationService.sendNotification(
                    receiverEmail,
                    "Ticket for ${ticketInfo.screeningWithMovie.movie.title}",
                    ticketInfo.toString()
            )
        }
    }

}
