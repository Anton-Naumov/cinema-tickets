package com.kpk.cinematickets.tickets.models

data class GroupTicketPurchaseRequest(
        val screeningId: Long,
        val seatIds: List<Long>
)
