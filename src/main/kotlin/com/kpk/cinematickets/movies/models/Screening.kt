package com.kpk.cinematickets.movies.models

import java.math.BigDecimal
import java.time.LocalDateTime

data class Screening (
        val id: Long,
        val time: LocalDateTime,
        val roomNumber: Long,
        val price: BigDecimal
)
