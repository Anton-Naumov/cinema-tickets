package com.kpk.cinematickets.tickets.models

import com.kpk.cinematickets.movies.models.Seat

data class GroupTicketPurchaseRequest(
        val screeningId: Long,
        val seats: List<Seat>
)
