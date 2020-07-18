package com.kpk.cinematickets.tickets.models

data class PurchasedGroupTicket(
        val screeningWithMovie: ScreeningWithMovie,
        val ticketsUniqueIds: List<String>
)
