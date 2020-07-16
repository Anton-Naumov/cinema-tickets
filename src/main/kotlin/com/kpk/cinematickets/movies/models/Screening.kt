package com.kpk.cinematickets.movies.models

import java.time.LocalDateTime

data class Screening (
        val time: LocalDateTime,
        val roomNumber: Long
)
