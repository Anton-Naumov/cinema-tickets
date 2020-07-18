package com.kpk.cinematickets.theater.models

data class Seat(
        val id: Long? = null,
        val number: Long,
        val isFree: Boolean
)
