package com.kpk.cinematickets.tickets.models

import java.time.LocalDateTime

data class Ticket(
        val uniqueId: String,
        val movieTitle: String,
        val screeningTime: LocalDateTime,
        val screeningRoom: Long,
        val seatNumber: Long
)
