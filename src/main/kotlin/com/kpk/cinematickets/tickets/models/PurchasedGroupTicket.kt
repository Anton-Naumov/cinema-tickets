package com.kpk.cinematickets.tickets.models

import java.lang.StringBuilder

data class PurchasedGroupTicket(
        val screeningWithMovie: ScreeningWithMovie,
        val purchasedSeats: List<PurchasedSeat>
) {
    override fun toString(): String {
        val ticketsData = StringBuilder()
        ticketsData.append(screeningWithMovie.movie)
        purchasedSeats.forEach {
            ticketsData.append("Seat: ${it.seatNumber}, ticket number: ${it.uniqueTicketId}")
        }
        return ticketsData.toString()
    }
}

data class PurchasedSeat(
        val uniqueTicketId: String,
        val seatNumber: Long
)
