package com.kpk.cinematickets.tickets.models

data class GroupTicket(
        val screeningId: Long,
        val seatNumbers: List<Long>
)
