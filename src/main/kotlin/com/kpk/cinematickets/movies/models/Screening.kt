package com.kpk.cinematickets.movies.models

import java.time.LocalDateTime

data class Screening (
        val id: Long,
        val time: LocalDateTime,
        val roomNumber: Long
)
